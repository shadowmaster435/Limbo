#version 150


const vec2 dash[] = vec2[](vec2(0, 2), vec2(1, 2), vec2(2, 2), vec2(2, 2), vec2(2, 2));
const vec2 decimal[] = vec2[](vec2(2, 4),vec2(2, 4),vec2(2, 4),vec2(2, 4),vec2(2, 4));
const vec2 zero[] = vec2[](vec2(1, 0),vec2(0, 1), vec2(0, 2), vec2(0, 3),vec2(2, 1), vec2(2, 2), vec2(2, 3), vec2(1, 4));
const vec2 one[] = vec2[](vec2(1, 0),vec2(1, 1), vec2(1, 2), vec2(1, 3), vec2(1, 4));
const vec2 two[] = vec2[](vec2(0, 0), vec2(1, 0), vec2(2, 1), vec2(1, 2), vec2(0, 3), vec2(0, 4), vec2(1, 4), vec2(2, 4));
const vec2 three[] = vec2[](vec2(0, 0), vec2(1, 0), vec2(2, 1), vec2(1, 2), vec2(2, 3), vec2(0, 4), vec2(1, 4));
const vec2 four[] = vec2[](vec2(0, 0), vec2(2, 0), vec2(0, 1), vec2(2, 1), vec2(1, 2), vec2(0, 2), vec2(2, 2), vec2(2, 3), vec2(2, 4));
const vec2 five[] = vec2[](vec2(0, 0),vec2(1, 0),vec2(2, 0), vec2(0, 1), vec2(0, 2),vec2(1, 2),vec2(2, 2),vec2(2, 3), vec2(0, 4),vec2(1, 4));
const vec2 six[] = vec2[](vec2(0, 0),vec2(0, 1),vec2(0, 2), vec2(1, 2),vec2(0, 3),vec2(2, 3), vec2(1, 4));
const vec2 seven[] = vec2[](vec2(0, 0),vec2(1, 0),vec2(2, 0), vec2(2, 1), vec2(1, 2), vec2(1, 3), vec2(0, 4));
const vec2 eight[] = vec2[](vec2(1, 0), vec2(0, 1), vec2(2, 1), vec2(1, 2), vec2(0, 3),vec2(2, 3), vec2(1, 4));
const vec2 nine[] = vec2[](vec2(1, 0), vec2(0, 1), vec2(2, 1), vec2(1, 2), vec2(2, 2), vec2(2, 3), vec2(2, 4));

int get_number_at(int index, int number) {
    float float_num = float(number);
    int current_int = 0;
    int result = 0;
    for (int i = -1; i < index; ++i) { // shift right amount of times according to index
        current_int = int(floor(float_num));
        float_num *= 0.1;
    }
    result = current_int % 10; // result is the ones digit of a number
    return result;
}

int get_number_length(int number) { // gets amount of digit places
    int result = 0;
    int current_divider = 1;
    while (float(number) / float(current_divider) > 0.0) {
        result += 1;
        current_divider *= 10;
    }
    return result;
}

int[10] construct_number_array(int number, bool reverse) { // change these if you need more digits displayed max digits is 10 due to integer limits
    int leng = get_number_length(number);
    int[10] result;
    for (int i = 0; i < leng; ++i) {

        if (reverse) {
            result[9 - i] = get_number_at(i, number);
        } else {
            result[i] = get_number_at(i - 1, number);
        }
    }
    return result;
}

int[10] construct_decimal_array(float inputfloat) {
    int[10] result;
    float cut_float = fract(inputfloat); // cut out non decimal numbers.
    for (int i = 0; i < 10; ++i) {
        if (mod(cut_float, 1.0) != 0.0) {
            cut_float *= 10.0;
            result[i] = int(cut_float);
            cut_float = fract(cut_float); // remove the part of the number now greater than one
        }
    }
    return result;
}


void draw_pixel(vec2 pixel_pos, vec2 frag_coord, vec2 scale, vec4 pixel_color, inout vec4 frag_color) {
    bvec2 g = greaterThanEqual(frag_coord, vec2(pixel_pos));
    bvec2 l = lessThanEqual(frag_coord, vec2(pixel_pos + (scale)));
    if (all(g) && all(l)) {
        frag_color = pixel_color;
    }
}

// all these exist because i couldn't think of a better way to handle the different array sizes
void draw_digit_eight_size(vec2[8] number_pixels, int index, vec2 pixel_pos, vec2 frag_coord, vec2 scale, vec4 pixel_color, inout vec4 frag_color) {
    vec2 index_ofs = pixel_pos + vec2(vec2(float(index * 4), 0.0) * vec2(scale)); // pixel offset for number
    for (int i = 0; i < number_pixels.length(); ++i) {
        vec2 number_pixel_pos = (number_pixels[i] * scale) + index_ofs;
        draw_pixel(number_pixel_pos, frag_coord, scale, pixel_color, frag_color);
    }
}

void draw_digit_five_size(vec2[5] number_pixels, int index, vec2 pixel_pos, vec2 frag_coord, vec2 scale, vec4 pixel_color, inout vec4 frag_color) {
    vec2 index_ofs = pixel_pos + vec2(vec2(float(index * 4), 0.0) * vec2(scale));
    for (int i = 0; i < number_pixels.length(); ++i) {
        vec2 number_pixel_pos = (number_pixels[i] * scale) + index_ofs;
        draw_pixel(number_pixel_pos, frag_coord, scale, pixel_color, frag_color);
    }
}

void draw_digit_seven_size(vec2[7] number_pixels, int index, vec2 pixel_pos, vec2 frag_coord, vec2 scale, vec4 pixel_color, inout vec4 frag_color) {
    vec2 index_ofs = pixel_pos + vec2(vec2(float(index * 4), 0.0) * vec2(scale));
    for (int i = 0; i < number_pixels.length(); ++i) {
        vec2 number_pixel_pos = (number_pixels[i] * scale) + index_ofs;
        draw_pixel(number_pixel_pos, frag_coord, scale, pixel_color, frag_color);
    }
}

void draw_digit_nine_size(vec2[9] number_pixels, int index, vec2 pixel_pos, vec2 frag_coord, vec2 scale, vec4 pixel_color, inout vec4 frag_color) {
    vec2 index_ofs = pixel_pos + vec2(vec2(float(index * 4), 0.0) * vec2(scale));
    for (int i = 0; i < number_pixels.length(); ++i) {
        vec2 number_pixel_pos = (number_pixels[i] * scale) + index_ofs;
        draw_pixel(number_pixel_pos, frag_coord, scale, pixel_color, frag_color);
    }
}

void draw_digit_ten_size(vec2[10] number_pixels, int index, vec2 pixel_pos, vec2 frag_coord, vec2 scale, vec4 pixel_color, inout vec4 frag_color) {
    vec2 index_ofs = pixel_pos + vec2(vec2(float(index * 4), 0.0) * vec2(scale));
    for (int i = 0; i < number_pixels.length(); ++i) {
        vec2 number_pixel_pos = (number_pixels[i] * scale) + index_ofs;
        draw_pixel(number_pixel_pos, frag_coord, scale, pixel_color, frag_color);
    }
}

void draw_digit_for_index(int digit, int index, vec2 pixel_pos, vec2 frag_coord, vec2 scale, vec4 pixel_color, inout vec4 frag_color) {
    switch (digit) {
        case 1: {
            //	frag_color.b = 0.0;
            draw_digit_five_size(one, index, pixel_pos, frag_coord, scale, pixel_color, frag_color);

            break;
        }
        case 2: {
            draw_digit_eight_size(two, index, pixel_pos, frag_coord, scale, pixel_color, frag_color);
            break;
        }
        case 3: {
            draw_digit_seven_size(three, index, pixel_pos, frag_coord, scale, pixel_color, frag_color);
            break;
        }
        case 4: {
            draw_digit_nine_size(four, index, pixel_pos, frag_coord, scale, pixel_color, frag_color);
            break;
        }
        case 5: {
            draw_digit_ten_size(five, index, pixel_pos, frag_coord, scale, pixel_color, frag_color);
            break;
        }
        case 6: {
            draw_digit_seven_size(six, index, pixel_pos, frag_coord, scale, pixel_color, frag_color);
            break;
        }
        case 7: {
            draw_digit_seven_size(seven, index, pixel_pos, frag_coord, scale, pixel_color, frag_color);
            break;
        }
        case 8: {
            draw_digit_seven_size(eight, index, pixel_pos, frag_coord, scale, pixel_color, frag_color);
            break;
        }
        case 9: {
            draw_digit_seven_size(nine, index, pixel_pos, frag_coord, scale, pixel_color, frag_color);
            break;
        }
        default: {
            draw_digit_eight_size(zero, index, pixel_pos, frag_coord, scale, pixel_color, frag_color);
            break;
        }
    }
}


void draw_number(int[10] number_array, vec2 offset, vec2 frag_coord, vec2 scale, vec4 pixel_color, inout vec4 frag_color, bool reverse) {
    bool first_non_zero_reached = false;
    int float_cut_index = 0;
    int digit_offset = 0; // pixel offset for current number: (digit_offset * scale.x) * 4.0
    for (int i = 0; i < number_array.length(); ++i) {
        int digit = number_array[i];
        if (float_cut_index > 3) { // limited due to strange display bug where the whole number appears at the end of the decimal portion.
            break;
        }
        if (reverse) {
            draw_digit_for_index(digit, i - digit_offset, offset + vec2((digit_offset * 4f) * scale.x, 0f), frag_coord, scale, pixel_color, frag_color);
            digit_offset += 1;
            float_cut_index += 1;
        } else {
            draw_digit_for_index(digit, i, offset, frag_coord, scale, pixel_color, frag_color);
        }
    }
}

void draw_integer(int integer, vec2 offset, vec2 frag_coord, vec2 scale, vec4 pixel_color, inout vec4 frag_color) {
    int[10] number_array = construct_number_array(abs(integer), true);
    draw_number(number_array, offset, frag_coord, scale, pixel_color, frag_color, false);
    if (integer < 0) { // draw dash if negative
        draw_digit_five_size(dash, -1, offset, frag_coord, scale, pixel_color, frag_color);
    }
}

const int[] allzero = int[](0,0,0,0,0,0,0,0,0,0);

void draw_float(float _float, vec2 offset, vec2 frag_coord, vec2 scale, vec4 pixel_color, inout vec4 frag_color) {
    int[10] whole_number_array = construct_number_array(abs(int(floor(_float))), true);
    int[10] partial_number_array = construct_decimal_array(abs(_float));
    if (_float < 1.0 && _float > -1.0) {
        whole_number_array = allzero; // fixes a display bug. where float and integer displays "merge"
    }
    draw_number(whole_number_array, offset, frag_coord, scale, pixel_color, frag_color, false);
    // draws decimal point
    draw_digit_five_size(decimal, 10, offset - vec2(scale.x * 2f, 0f), frag_coord, scale, pixel_color, frag_color);

    draw_number(partial_number_array, offset + vec2(scale.x * 42f, 0f), frag_coord, scale, pixel_color, frag_color, true);
    if (_float < 0.0) { // draw dash if negative
        draw_digit_five_size(dash, -1, offset, frag_coord, scale, pixel_color, frag_color);
    }
}



