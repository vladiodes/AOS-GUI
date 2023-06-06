from PIL import Image, ImageDraw

def display(state, filename = None,is_merging_images = False):
    # Define cell and image dimensions
    cell_size = 80
    num_rows = 6
    num_cols = 6
    image_width = num_cols * cell_size
    image_height = num_rows * cell_size

    # Create a blank image
    image = Image.new("RGB", (image_width, image_height), "white")
    draw = ImageDraw.Draw(image)

    # Define toy colors
    toy_colors = {
        "red": (255, 0, 0),
        "black": (0, 0, 0),
        "blue": (0, 0, 255),
        "green": (0, 255, 0)
    }


    # Draw labels for toy rows
    for row in range(num_rows - 1):
        toy_key = f"toy{row + 1}"
        if toy_key in state:
            toy = state[toy_key]
            toy_type = toy["type"]
            label = f"Toy{row + 1}: {toy_type.capitalize()}"
            draw.text((10, row * cell_size + 10), label, fill=(0, 0, 0))

    # Draw label for robot row
    draw.text((10, (num_rows - 1) * cell_size + 10), "Robot", fill=(0, 0, 0))

    # Draw labels for location columns
    for col in range(num_cols):
        label = f"Location {col}"
        draw.text((col * cell_size + 10, (num_rows - 1) * cell_size - 30), label, fill=(0, 0, 0))

    # Draw rectangles for toys
    for row in range(num_rows - 1):
        toy_key = f"toy{row + 1}"
        if toy_key in state:
            toy = state[toy_key]
            toy_location = toy["location"]
            toy_type = toy["type"]
            toy_color = toy_colors[toy_type]
            x1 = toy_location * cell_size
            y1 = row * cell_size
            x2 = (toy_location + 1) * cell_size
            y2 = (row + 1) * cell_size
            draw.rectangle([x1, y1, x2, y2], fill=toy_color)

    # Draw circle for robot
    robot_location = state["robotLocation"]
    x1 = robot_location * cell_size + cell_size // 4
    y1 = (num_rows - 1) * cell_size + cell_size // 4
    x2 = (robot_location + 1) * cell_size - cell_size // 4
    y2 = num_rows * cell_size - cell_size // 4
    draw.ellipse([x1, y1, x2, y2], fill=(0, 0, 0))

    # Show the plot
    image.save(filename, format='PNG')
