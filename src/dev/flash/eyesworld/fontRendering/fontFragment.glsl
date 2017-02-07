#version 400

in vec2 pass_textureCoords;

out vec4 out_colour;

uniform vec3 colour;
uniform sampler2D fontAtlas;


const float width = 0.5;//distance from center
const float edge = 0.1;//width of fading area


const float borderWidth = 0.0;//0.5 //0.7
const float borderEdge = 0.1;//0.4

const vec2 offset = vec2(0.00, 0.00);//dropshadow0.004, 0.004


const vec3 outlineColour = vec3(1.0, 0.0, 0.0);

void main(void){

	float distance = 1.0 - texture(fontAtlas, pass_textureCoords).a;
	float alpha = 1.0 - smoothstep(width, width + edge, distance);

	float distance2 = 1.0 - texture(fontAtlas, pass_textureCoords + offset).a;
	float outlineAlpha = 1.0 - smoothstep(borderWidth, borderWidth + borderEdge, distance2);

	float finalAlpha = alpha + (1.0 - alpha) * outlineAlpha;
	vec3 finalColour = mix(outlineColour, colour, alpha/finalAlpha);

	out_colour = vec4(finalColour, finalAlpha);
}