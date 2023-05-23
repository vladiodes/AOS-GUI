# Here goes user's code - the display function.
# def display(x): ....

def save_image(state, filename):
    display(state, filename=filename, is_merging_images=True)


def save_images(states, filename_prefix = 'image'):
    for i,state in enumerate(states):
        save_image(state,filename_prefix + str(i))
        

from PIL import Image

def merge_images(states):
    belief_size = len(states)
    save_images(states)
    images = [Image.open('image' + str(i)) for i in range(belief_size)]

    # Ensure all images have the same size
    image_size = images[0].size
    for image in images[1:]:
        image = image.resize(image_size)

    merged_image = Image.new('RGB', image_size)

    pixels = [image.load() for image in images]

    # Iterate over each pixel in the input images and calculate the average RGB value
    for x in range(image_size[0]):
        for y in range(image_size[1]):
            r = sum([pixel[x, y][0] for pixel in pixels]) // belief_size
            g = sum([pixel[x, y][1] for pixel in pixels]) // belief_size
            b = sum([pixel[x, y][2] for pixel in pixels]) // belief_size
            merged_image.putpixel((x, y), (r, g, b))

    delete_images(['image' + str(i) for i in range(belief_size)])
    merged_image.show()

import os

def delete_images(images):
    for image in images:
        if os.path.exists(image):
            os.remove(image)


# Append from java the following code:
# states = list of states (dictionaries)
# merge_images(states)