#version 400 core

in vec2 textureCoords;

out vec4 out_Color;

uniform sampler2D reflectionTexture;
uniform sampler2D refractionTexture;


void main(void) {

	vec4 reflectionColour = texture(reflectionTexture, textureCoords);
	vec4 refractionColour = texture(refractionTexture, textureCoords);

	out_Color = mix(reflectionColour, refractionColour, 0.5);

}