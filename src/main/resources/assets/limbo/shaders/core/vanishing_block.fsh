#version 150



uniform sampler2D DiffuseSampler;

uniform ivec4 ViewPort;

in vec2 texCoord;

uniform float time;

out vec4 fragColor;

#moj_import <debugger.glsl>



void main()
{
    ///float _float, ivec2 offset, vec2 frag_coord, ivec2 scale, vec4 pixel_color, inout vec4 frag_color
    vec4 tex = texture(DiffuseSampler, texCoord);
    draw_float(1.0f, ivec2(0,0), texCoord, ivec2(4,4), vec4(1f,1f,1f,1f), tex);
    fragColor = vec4(1,1,1,1);

}