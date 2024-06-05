#version 120

uniform sampler2D DiffuseSampler;
uniform sampler2D Depth;
uniform sampler2D Depth2;


uniform vec4 ColorModulate;

varying vec2 texCoord;

void main() {
    vec4 c1 = texture2D(Depth, texCoord);
    vec4 c2 = texture2D(Depth2, texCoord);
    vec4 c3 = texture2D(DiffuseSampler, texCoord);
    if (texCoord.x > 0.5) {
        gl_FragColor = texture2D(Depth2, texCoord);
    } else {
        gl_FragColor = texture2D(DiffuseSampler, texCoord);

    }


    //   gl_FragColor = texture2D(Depth, texCoord);

}