#version 400 core

in vec4 clipSpace;
in vec2 textureCoords;

out vec4 out_Color;

uniform sampler2D reflectionTexture;
uniform sampler2D refractionTexture;
uniform sampler2D dudvMap;

const float waveStr = 0.02;

void main(void) {

	vec2 ndc = (clipSpace.xy/clipSpace.w)/2.0+0.5;

	vec2 reflectTexCoords = vec2(ndc.x, -ndc.y);
	vec2 refractTexCoords = ndc;

	vec2 distortion1 = (texture(dudvMap, vec2(textureCoords.x, textureCoords.y)).rg*2.0-1.0)*waveStr;

	reflectTexCoords += distortion1;
	refractTexCoords += distortion1;
	reflectTexCoords.x = clamp(reflectTexCoords.x,0.001,0.999);
	reflectTexCoords.y = clamp(reflectTexCoords.y,-0.999,-0.001);
	refractTexCoords = clamp(refractTexCoords,0.001,0.999);


	vec4 reflectionColour = texture(reflectionTexture, reflectTexCoords);
	vec4 refractionColour = texture(refractionTexture, refractTexCoords);

	out_Color = mix(reflectionColour, refractionColour, 0.5);

}