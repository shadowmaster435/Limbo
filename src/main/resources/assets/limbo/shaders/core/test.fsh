#version 150

uniform sampler2D Sampler0;
uniform sampler2D SamplerTest;

uniform vec4 ColorModulator;
uniform float time;

uniform vec3 playerpos;
in vec4 vertexColor;
in vec2 texCoord0;
in vec2 texCoord2;
uniform vec3 Center;



out vec4 fragColor;
#moj_import <debugger.glsl>



void main() {
    vec2 ofs = texCoord0; //+ vec2(cos((texCoord0.y * 4f) + time) / 4f, sin((texCoord0.x * 4f) + time) / 4f);
    vec4 color = texture(Sampler0, ofs);
    if (color.a < 0.1) {
        discard;
    }

   // fragColor = color;
    draw_float(1.12f + sin(time) * 1.0f, vec2(0.05,0), texCoord0, vec2(0.125 / 8f, 0.125/ 8f), vec4(1f,1f,1f,1f), fragColor);
    draw_float(Center.x, vec2(0.05,0.12), texCoord0, vec2(0.125 / 8f, 0.125/ 8f), vec4(1f,0f,0f,1f), fragColor);
    draw_float(Center.y, vec2(0.05,0.24), texCoord0, vec2(0.125 / 8f, 0.125/ 8f), vec4(0f,1f,0f,1f), fragColor);

    draw_float(Center.z, vec2(0.05,0.36), texCoord0, vec2(0.125 / 8f, 0.125/ 8f), vec4(0f,0f,1f,1f), fragColor);

}