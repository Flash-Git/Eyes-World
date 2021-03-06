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
uniform sampler2D depthMap;
uniform vec3 lightColour;

uniform float moveFactor;

const float waveStr = 0.04;
const float shineDamper = 20.0;
const float reflectivity = 0.5;

void main(void) {
	vec2 ndc = (clipSpace.xy / clipSpace.w) / 2.0 + 0.5;

	vec2 reflectTexCoords = vec2(ndc.x, -ndc.y);
	vec2 refractTexCoords = ndc;

	float near = 0.1;
	float far = 6000.0;

	float depth = texture(depthMap, refractTexCoords).r;//0-1 (non linear)
	float floorDistance = 2.0 * near * far / (far + near - (2.0 * depth - 1.0) * (far - near));//convert to distance

	depth = gl_FragCoord.z;//gives distance
	float waterDistance = 2.0 * near * far / (far + near - (2.0 * depth - 1.0) * (far - near));//convert to distance
	float waterDepth = floorDistance - waterDistance;

	vec2 distortedTexCoords = texture(dudvMap, vec2(textureCoords.x + moveFactor, textureCoords.y)).rg*0.1;
    distortedTexCoords = textureCoords + vec2(distortedTexCoords.x, distortedTexCoords.y+moveFactor);
    vec2 totalDistortion = (texture(dudvMap, distortedTexCoords).rg * 2.0 - 1.0) * waveStr * clamp(waterDepth/35.0, 0.0, 1.0);

	reflectTexCoords += totalDistortion;
	refractTexCoords += totalDistortion;
	reflectTexCoords.x = clamp(reflectTexCoords.x,0.001,0.999);
	reflectTexCoords.y = clamp(reflectTexCoords.y,-0.999,-0.001);
	refractTexCoords = clamp(refractTexCoords,0.001,0.999);


	vec4 reflectionColour = texture(reflectionTexture, reflectTexCoords);
	vec4 refractionColour = texture(refractionTexture, refractTexCoords);

	vec4 murkyWaterColour = vec4(0.001, 0.015, 0.3, 1.0);//should be uniform
	refractionColour = mix(refractionColour, murkyWaterColour, clamp(waterDepth/100.0, 0.0, 1.0));

	vec4 normalMapColour = texture(normalMap, distortedTexCoords);
	vec3 normal = vec3(normalMapColour.r * 2.0 - 1.0, normalMapColour.b * 3.0, normalMapColour.g * 2.0 - 1.0);//multiplying the b for the normals to point further upward
	normal = normalize(normal);

	//fresnel calc
	vec3 viewVector = normalize(toCameraVector);
	float refractiveFactor = dot(viewVector, normal);
	refractiveFactor = pow(refractiveFactor, 3.0);//changing this value changes how reflective/refractive the water is
	refractiveFactor = clamp(refractiveFactor, 0.0, 1.0);


	vec3 reflectedLight = reflect(normalize(fromLightVector), normal);
	float specular = max(dot(reflectedLight, viewVector), 0.0);
	specular = pow(specular, shineDamper);
	vec3 specularHighlights = lightColour * specular * reflectivity * clamp(waterDepth/10.0, 0.0, 1.0);;


	out_Color = mix(reflectionColour, refractionColour, refractiveFactor);
	out_Color = mix(out_Color, vec4(0.0, 0.4, 0.5, 1.0), 0.2) + vec4(specularHighlights, 0.0);//Adding blue/gree tint
	out_Color.a = clamp(waterDepth/7.0, 0.0, 1.0);
}