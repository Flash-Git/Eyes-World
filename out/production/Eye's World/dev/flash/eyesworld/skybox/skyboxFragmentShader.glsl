#version 400

in vec3 textureCoords;
out vec4 out_Color;

uniform samplerCube cubeMap;
uniform vec3 fogColour;

const float lowerLimit = -10.0;
const float upperLimit = 35.0;

void main(void){
    vec4 finalColour = texture(cubeMap, textureCoords);

	float factor = (textureCoords.y - lowerLimit)/(upperLimit - lowerLimit);
	factor = clamp(factor, 0.0, 1.0);

    out_Color = mix(vec4(fogColour, 1.0), finalColour, factor);
}