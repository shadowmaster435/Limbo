#version 150

// The main texture
uniform sampler2D DiffuseSampler;
// The depth map
uniform sampler2D DepthSampler;

uniform sampler2D Floor;
uniform sampler2D HMTest;

// Position of the camera
uniform vec3 CameraPosition;

// Position of the center of the ping effect
uniform vec3 Center;
// Time in seconds (+ tick delta)
uniform float STime;
uniform float StaticInfluence;
uniform float OrbScale;
uniform float EyeLookDelta;

// The magic matrix to get world coordinates from pixel ones
uniform mat4 InverseTransformMatrix;
// The size of the viewport (typically, [0,0,1080,720])
uniform ivec4 ViewPort;

in vec2 texCoord;
in vec4 vPosition;

out vec4 fragColor;

vec4 CalcEyeFromWindow(in float depth)
{
    // derived from https://www.khronos.org/opengl/wiki/Compute_eye_space_from_window_space
    // ndc = Normalized Device Coordinates
    vec3 ndcPos;
    ndcPos.xy = ((2.0 * gl_FragCoord.xy) - (2.0 * ViewPort.xy)) / (ViewPort.zw) - 1;
    ndcPos.z = (2.0 * depth - gl_DepthRange.near - gl_DepthRange.far) / (gl_DepthRange.far - gl_DepthRange.near);
    vec4 clipPos = vec4(ndcPos, 1.);
    vec4 homogeneous = InverseTransformMatrix * clipPos;
    vec4 eyePos = vec4(homogeneous.xyz / homogeneous.w, homogeneous.w);
    return eyePos;
}
vec3 pixelate(vec3 coord, ivec3 pixelate_resolution) {
    vec3 uv = vec3(coord.x, coord.y, coord.z);
    float x = round(uv.x * float(pixelate_resolution.x)) / float(pixelate_resolution.x);
    float y = round(uv.y * float(pixelate_resolution.y)) / float(pixelate_resolution.y);
    float z = round(uv.z * float(pixelate_resolution.z)) / float(pixelate_resolution.z);

    return vec3(x, y, z);
}

float pixelate_num(float coord, int pixelate_resolution) {
    float x = round(coord * float(pixelate_resolution)) / float(pixelate_resolution);

    return x;
}

//	Simplex 4D Noise
//	by Ian McEwan, Stefan Gustavson (https://github.com/stegu/webgl-noise)
//
vec4 permute(vec4 x){return mod(((x*34.0)+1.0)*x, 289.0);}
float permute(float x){return floor(mod(((x*34.0)+1.0)*x, 289.0));}
vec4 taylorInvSqrt(vec4 r){return 1.79284291400159 - 0.85373472095314 * r;}
float taylorInvSqrt(float r){return 1.79284291400159 - 0.85373472095314 * r;}

vec4 grad4(float j, vec4 ip){
    const vec4 ones = vec4(1.0, 1.0, 1.0, -1.0);
    vec4 p,s;

    p.xyz = floor( fract (vec3(j) * ip.xyz) * 7.0) * ip.z - 1.0;
    p.w = 1.5 - dot(abs(p.xyz), ones.xyz);
    s = vec4(lessThan(p, vec4(0.0)));
    p.xyz = p.xyz + (s.xyz*2.0 - 1.0) * s.www;

    return p;
}

float snoise(vec4 v){
    const vec2  C = vec2( 0.138196601125010504,  // (5 - sqrt(5))/20  G4
    0.309016994374947451); // (sqrt(5) - 1)/4   F4
    // First corner
    vec4 i  = floor(v + dot(v, C.yyyy) );
    vec4 x0 = v -   i + dot(i, C.xxxx);

    // Other corners

    // Rank sorting originally contributed by Bill Licea-Kane, AMD (formerly ATI)
    vec4 i0;

    vec3 isX = step( x0.yzw, x0.xxx );
    vec3 isYZ = step( x0.zww, x0.yyz );
    //  i0.x = dot( isX, vec3( 1.0 ) );
    i0.x = isX.x + isX.y + isX.z;
    i0.yzw = 1.0 - isX;

    //  i0.y += dot( isYZ.xy, vec2( 1.0 ) );
    i0.y += isYZ.x + isYZ.y;
    i0.zw += 1.0 - isYZ.xy;

    i0.z += isYZ.z;
    i0.w += 1.0 - isYZ.z;

    // i0 now contains the unique values 0,1,2,3 in each channel
    vec4 i3 = clamp( i0, 0.0, 1.0 );
    vec4 i2 = clamp( i0-1.0, 0.0, 1.0 );
    vec4 i1 = clamp( i0-2.0, 0.0, 1.0 );

    //  x0 = x0 - 0.0 + 0.0 * C
    vec4 x1 = x0 - i1 + 1.0 * C.xxxx;
    vec4 x2 = x0 - i2 + 2.0 * C.xxxx;
    vec4 x3 = x0 - i3 + 3.0 * C.xxxx;
    vec4 x4 = x0 - 1.0 + 4.0 * C.xxxx;

    // Permutations
    i = mod(i, 289.0);
    float j0 = permute( permute( permute( permute(i.w) + i.z) + i.y) + i.x);
    vec4 j1 = permute( permute( permute( permute (
    i.w + vec4(i1.w, i2.w, i3.w, 1.0 ))
    + i.z + vec4(i1.z, i2.z, i3.z, 1.0 ))
    + i.y + vec4(i1.y, i2.y, i3.y, 1.0 ))
    + i.x + vec4(i1.x, i2.x, i3.x, 1.0 ));
    // Gradients
    // ( 7*7*6 points uniformly over a cube, mapped onto a 4-octahedron.)
    // 7*7*6 = 294, which is close to the ring size 17*17 = 289.

    vec4 ip = vec4(1.0/294.0, 1.0/49.0, 1.0/7.0, 0.0) ;

    vec4 p0 = grad4(j0,   ip);
    vec4 p1 = grad4(j1.x, ip);
    vec4 p2 = grad4(j1.y, ip);
    vec4 p3 = grad4(j1.z, ip);
    vec4 p4 = grad4(j1.w, ip);

    // Normalise gradients
    vec4 norm = taylorInvSqrt(vec4(dot(p0,p0), dot(p1,p1), dot(p2, p2), dot(p3,p3)));
    p0 *= norm.x;
    p1 *= norm.y;
    p2 *= norm.z;
    p3 *= norm.w;
    p4 *= taylorInvSqrt(dot(p4,p4));

    // Mix contributions from the five corners
    vec3 m0 = max(0.6 - vec3(dot(x0,x0), dot(x1,x1), dot(x2,x2)), 0.0);
    vec2 m1 = max(0.6 - vec2(dot(x3,x3), dot(x4,x4)            ), 0.0);
    m0 = m0 * m0;
    m1 = m1 * m1;
    return 49.0 * ( dot(m0*m0, vec3( dot( p0, x0 ), dot( p1, x1 ), dot( p2, x2 )))
    + dot(m1*m1, vec2( dot( p3, x3 ), dot( p4, x4 ) ) ) ) ;

}
float PHI = 1.61803398874989484820459;  // Φ = Golden Ratio

float rand(in vec4 p) {
    return fract(sin(p.x*1234. + p.y*2345. + p.z*3456. + p.w*4567.) * 5678.);
}

vec3 get_heightmap_pos(vec4 col_pos) {
    return (col_pos.rgb * 255) ;
}


bool can_see_sky(vec3 pix_pos, vec4 col_pos) {
    vec3 pos = get_heightmap_pos(col_pos);
    return pix_pos.y + 0.05  >= pos.y;
}

void main()
{
    //sin(STime)
    vec4 tex = texture(DiffuseSampler, texCoord);

    vec3 ndc = vPosition.xyz / vPosition.w; //perspective divide/normalize
    vec2 viewportCoord = ndc.xy * 0.5 + 0.5; //ndc is -1 to 1 in GL. scale for 0 to 1

    // Depth fading
    float sceneDepth = texture(DepthSampler, viewportCoord).x;
    vec3 pixelPosition = CalcEyeFromWindow(sceneDepth).xyz + (CameraPosition);

    // Ping effect: color pixels that are some distance away from the center
    float xs = pixelate_num(pixelPosition.x, 16) + pixelate_num(sin((pixelate_num(STime, 20) * 4.) + pixelate_num(pixelPosition.z, 16) * 4.) / 4.0, 16);
    float xy = pixelate_num(pixelPosition.y, 16) + pixelate_num((cos((pixelate_num(STime, 20) * 4.) + (pixelate_num(pixelPosition.z, 16) + pixelate_num(pixelPosition.x, 16)) * 4.) / 4.0), 16);
    float zs = pixelate_num(pixelPosition.z, 16) + pixelate_num(sin((pixelate_num(STime, 20) * 4.) + pixelate_num(pixelPosition.x, 16) * 4.) / 4.0, 16);
    float d = distance(pixelate(vec3(xs, pixelPosition.y, zs), ivec3(16)), Center);
   // d /= mod(STime, 20); // Scale the world to make the ping diffuse over time
    vec3 rainbow = vec3(sin(STime), cos(STime), sin(STime + 1.)) * smoothstep(2, 3., d) * smoothstep(4, 3., d);
    //d /= mod(1, OrbScale);
    float rad = mix(0f,5.5, (1.0 - OrbScale));
    float rad_fade = 1.0 - (d / rad);
    float a = ((sin((texCoord.y * 16.0) + STime)) / 16.0) * sin(rad_fade * 3.14);
    vec4 atex = texture(DiffuseSampler, vec2(texCoord.x, texCoord.y));
    vec3 pixelated = pixelate(pixelPosition, ivec3(16));

    vec4 texfloor = texture2D(Floor, vec2(pixelated.x + (pixelated.y - pixelated.z),  pixelated.z + (pixelated.y) ));
    if (pixelate_num(d, 16) < rad) {
        fragColor = texture(Floor, vec2(texCoord.x, texCoord.y));
        if (rad_fade >= 0.0 && rad_fade < 0.025) {
            fragColor.rgb = vec3(1.0);
        } else {
            if ((tex.r >= 0.95 && tex.g <= 0.1 && tex.b <= 0.1)) {
                fragColor.rgb = vec3(1.0, 0.0,0.0);
            } else {
                float n = ((snoise(vec4(pixelate(pixelPosition, ivec3(16)) * 4f, STime * 8f)) * 0.5) + 0.5) * StaticInfluence;
                fragColor = texfloor + vec4(n);
            }
        }
    } else {
        fragColor = tex;
    }
    if (EyeLookDelta > 0.0) {
        float n = ((snoise(vec4(pixelate(pixelPosition, ivec3(16)) * 4f, STime * 8f)) * 0.5) + 0.5);
        if (n < max(0.0, EyeLookDelta + 0.1) && !((tex.r >= 0.95 && tex.g <= 0.1 && tex.b <= 0.1))) {
            fragColor.rgb = vec3(0.0);
        }
    }
    float range = 128;
    vec2 non_skippy_camera_pos = CameraPosition.xz ;

    vec2 camera_smooth = (mod(CameraPosition.zx , vec2(1)));
    vec2 i = mod((((pixelPosition.zx - CameraPosition.zx) - 0.5) + camera_smooth) - (range / 2f), range) / (range - 1);
    if (can_see_sky(pixelPosition, texture(HMTest, i))) {
        fragColor.r = 0;
    }
        //fragColor = texture(HMTest, i);
}