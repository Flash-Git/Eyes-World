#version 400 core

in vec4 clipSpace;
in vec2 textureCoords;
in vec3 toCameraVector;

out vec4 out_Color;

uniform sampler2D reflectionTexture;
uniform sampler2D refractionTexture;
uniform sampler2D dudvMap;

uniform float moveFactor;

const float waveStr = 0.01;

void main(void) {

	vec2 ndc = (clipSpace.xy/clipSpace.w)/2.0+0.5;

	vec2 reflectTexCoords = vec2(ndc.x, -ndc.y);
	vec2 refractTexCoords = ndc;

	vec2 distortion1 = (texture(dudvMap, vec2(textureCoords.x+moveFactor, textureCoords.y)).rg*2.0-1.0)*waveStr;
	vec2 distortion2 = (texture(dudvMap, vec2(-textureCoords.x+moveFactor, textureCoords.y+moveFactor)).rg*2.0-1.0)*waveStr;
	vec2 totalDistortion = distortion1+distortion2;

	reflectTexCoords += totalDistortion;
	refractTexCoords += totalDistortion;
	reflectTexCoords.x = clamp(reflectTexCoords.x,0.001,0.999);
	reflectTexCoords.y = clamp(reflectTexCoords.y,-0.999,-0.001);
	refractTexCoords = clamp(refractTexCoords,0.001,0.999);


	vec4 reflectionColour = texture(reflectionTexture, reflectTexCoords);
	vec4 refractionColour = texture(refractionTexture, refractTexCoords);

	vec3 viewVector = normalize(toCameraVector);
	float refractiveFactor = dot(viewVector, vec3(0.0, 1.0, 0.0));
	refractiveFactor = pow(refractiveFactor, 3.0);//changing this value changes how reflective/refractive the water is

	out_Color = mix(reflectionColour, refractionColour, refractiveFactor);
	out_Color = mix(out_Color, vec4(0.0, 0.4, 0.5, 1.0), 0.2);//Adding blue/gree tint

}