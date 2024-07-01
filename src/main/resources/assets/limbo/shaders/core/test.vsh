#version 150

in vec3 Position;
in vec4 Color;
in vec2 UV0;
in vec2 UV2;

uniform mat4 ModelViewMat;
uniform mat4 ProjMat;
in vec3 playerpos;

out vec4 vertexColor;
out vec2 texCoord0;
out vec2 texCoord2;
uniform float time;


void main(){
   // gl_Position = ProjMat * ModelViewMat * vec4(Position + vec3(0,sin((Position.z * 16f) + (time / 1f)) / 16f,0), 1.0);
    gl_Position = ProjMat * ModelViewMat * vec4(Position, 1.0);

    vertexColor = Color;
    texCoord0 = UV0 ;
    texCoord2 = UV2;
}