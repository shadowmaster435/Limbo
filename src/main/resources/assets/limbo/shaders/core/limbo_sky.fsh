#version 150
uniform sampler2D Sampler0;


#moj_import <util.glsl>

uniform vec4 ColorModulator;
uniform float time;
in vec2 texCoord0;

in vec4 texProj0;




out vec4 fragColor;


float get_noise_layer(float ofs) {
    return snoise3d(vec3(vec2(texCoord0 + ofs) * 32f, time / 256f));
}
float get_noise_layer2(vec2 scale, float ofs) {
    return snoise3d(vec3(vec2((texCoord0 * scale) + ofs) * 16f, time / 256f));
}
void main() {
    float n = get_noise_layer(get_noise_layer2(vec2(2,2), 40f));
    float n2 = get_noise_layer2(vec2(0.5,0.5), 32);
    fragColor.rgb = vec3((n * abs(n2)) - 0.65);


}


