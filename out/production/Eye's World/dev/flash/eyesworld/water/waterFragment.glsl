#version 400 core

in vec4 clipSpace;
in vec2 textureCoords;
in vec3 toCameraVector;
in vec3 fromLightVector;

out vec4 out_Color;

uniform sampler2D reflectionTexture;
uniform sampler2D refractionTexture;
uniform sampler2D dudvMap;
uniform sampler2D normalMap;
uniform vec3 lightColour;

uniform float moveFactor;

const float waveStr = 0.015;
const float shineDamper = 20.0;
const float reflectivity = 0.6;

void main(void) {

	vec2 ndc = (clipSpace.xy/clipSpace.w)/2.0+0.5;

	vec2 reflectTexCoords = vec2(ndc.x, -ndc.y);
	vec2 refractTexCoords = ndc;

	vec2 distortedTexCoords = texture(dudvMap, vec2(textureCoords.x + moveFactor, textureCoords.y)).rg*0.1;
    distortedTexCoords = textureCoords + vec2(distortedTexCoords.x, distortedTexCoords.y+moveFactor);
    vec2 totalDistortion = (texture(dudvMap, distortedTexCoords).rg * 2.0 - 1.0) * waveStr;

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

	vec4 normalMapColour = texture(normalMap, distortedTexCoords);
	vec3 normal = vec3(normalMapColour.r * 2.0 - 1.0, normalMapColour.b, normalMapColour.g * 2.0 - 1.0);
	normal = normalize(normal);


	vec3 reflectedLight = reflect(normalize(fromLightVector), normal);
	float specular = max(dot(reflectedLight, viewVector), 0.0);
	specular = pow(specular, shineDamper);
	vec3 specularHighlights = lightColour * specular * reflectivity;


	out_Color = mix(reflectionColour, refractionColour, refractiveFactor);
	out_Color = mix(out_Color, vec4(0.0, 0.4, 0.5, 1.0), 0.2) + vec4(specularHighlights, 0.0);//Adding blue/gree tint

}