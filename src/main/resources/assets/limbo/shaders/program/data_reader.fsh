#version 150


ivec2 get_buffer_dimensions(sampler2D data_buffer) {
   return textureSize(data_buffer, 0);
}

int buffer_pos_to_index(ivec2 pos, ivec2 dims) {
    return pos.x + (dims.y * pos.y);
}

ivec2 index_to_buffer_pos(int index, ivec2 dims) {
    return ivec2(index % dims.x, int(floor(index / dims.y)));
}

ivec2 read_type(int index, sampler2D data_buffer) {
    return index_to_buffer_pos(index, get_buffer_dimensions(data_buffer)).rg;
}

int read_int(int index, sampler2D data_buffer) {
    ivec2 dims = get_buffer_dimensions(data_buffer);
    ivec4 color = texture(data_buffer, index_to_buffer_pos(index, dims) / dims);
    return (color.r << 24) | (color.g << 16) | (color.b << 8) | color.a;
}

float read_float(int index, sampler2D data_buffer) {
    ivec2 dims = get_buffer_dimensions(data_buffer);
    ivec4 color = texture(data_buffer, index_to_buffer_pos(index, dims) / dims);
    return intBitsToFloat((color.r << 24) | (color.g << 16) | (color.b << 8) | color.a);
}

vec3 read_vec3(int index, sampler2D data_buffer) {
    float x = read_float(index, data_buffer);
    float y = read_float(index + 1, data_buffer);
    float z = read_float(index + 2, data_buffer);
    return vec3(x, y, z);
}

ivec3 read_ivec3(int index, sampler2D data_buffer) {
    int x = read_int(index, data_buffer);
    int y = read_int(index + 1, data_buffer);
    int z = read_int(index + 2, data_buffer);
    return ivec3(x, y, z);
}

vec2 read_vec2(int index, sampler2D data_buffer) {
    float x = read_float(index, data_buffer);
    float y = read_float(index + 1, data_buffer);
    return vec2(x, y);
}

ivec2 read_ivec2(int index, sampler2D data_buffer) {
    int x = read_int(index, data_buffer);
    int y = read_int(index + 1, data_buffer);
    return ivec2(x, y);
}

void read_int_array(inout int index, in int length, in sampler2D data_buffer, out int result, out bool hit_end) {
    if (index < length) {
        result = read_int(index, data_buffer);
        index += 1;
        hit_end = false;
    } else {
        hit_end = true;
    }
}

void read_float_array(inout int index, in int length, in sampler2D data_buffer, out float result, out bool hit_end) {
    if (index < length) {
        result = read_float(index, data_buffer);
        index += 1;
        hit_end = false;
    } else {
        hit_end = true;
    }
}

void read_ivec2_array(inout int index, in int length, in sampler2D data_buffer, out ivec2 result, out bool hit_end) {
    if (index / 2 < length) {
        result = read_ivec2(index, data_buffer);
        index += 2;
        hit_end = false;
    } else {
        hit_end = true;
    }
}

void read_vec2_array(inout int index, in int length, in sampler2D data_buffer, out vec2 result, out bool hit_end) {
    if (index / 2 < length) {
        result = read_vec2(index, data_buffer);
        index += 2;
        hit_end = false;
    } else {
        hit_end = true;
    }
}

void read_ivec3_array(inout int index, in int length, in sampler2D data_buffer, out ivec3 result, out bool hit_end) {
    if (index / 3 < length) {
        result = read_ivec3(index, data_buffer);
        index += 3;
        hit_end = false;
    } else {
        hit_end = true;
    }
}

void read_vec3_array(inout int index, in int length, in sampler2D data_buffer, out vec3 result, out bool hit_end) {
    if (index / 3 < length) {
        result = read_vec3(index, data_buffer);
        index += 3;
        hit_end = false;
    } else {
        hit_end = true;
    }
}

int read_x_instance_of_int(int x_occurence, sampler2D data_buffer) {
    int current_occurence = 0;
    int current_index = 0;
    int result = 0;
    ivec2 dims = get_buffer_dimensions(data_buffer);
    while(current_occurence < x_occurence && current_index < dims.x * dims.y) {
        ivec2 type = read_type(current_index, data_buffer);
        if (type.x == 1) {
            if (current_occurence >= x_occurence) {
                return read_int(current_index + 1);
            }
            current_occurence += 1;
        } else {
            current_index += type.length() + 1;
        }
    }
    return result;
}

float read_x_instance_of_float(int x_occurence, sampler2D data_buffer) {
    int current_occurence = 0;
    int current_index = 0;
    float result = 0;
    ivec2 dims = get_buffer_dimensions(data_buffer);
    while(current_occurence < x_occurence && current_index < dims.x * dims.y) {
        ivec2 type = read_type(current_index, data_buffer);
        if (type.x == 2) {
            if (current_occurence >= x_occurence) {
                return read_float(current_index + 1);
            }
            current_occurence += 1;
        } else {
            current_index += type.length() + 1;
        }
    }
    return result;
}


