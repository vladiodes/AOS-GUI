from PIL import Image, ImageDraw

def display(game_state):
    # Define the dimensions and positions
    width = 700
    height = 400
    padding = 30
    location_width = (width - padding * 2) / 5
    toy_size = min(location_width, 80)
    text_padding = 10
    label_spacing = 5

    # Create a blank image
    image = Image.new('RGB', (width, height), color='white')
    draw = ImageDraw.Draw(image)

    # Draw the locations and their contents
    for i, location in enumerate(range(1, 6)):
        x = padding + i * location_width
        y = padding + toy_size + text_padding
        draw.rectangle([x, y, x + location_width, y + toy_size], outline='black')
        draw.text((x + location_width / 2, y + toy_size / 2 - toy_size / 4 - text_padding), f"Location {location}", fill='black', anchor='middle')

        toys_at_location = []
        for toy in ['toy1', 'toy2', 'toy3', 'toy4']:
            if game_state[toy]['location'] == location:
                toys_at_location.append((toy, game_state[toy]['type']))

        if toys_at_location:
            toy_height = toy_size / (len(toys_at_location) + 1)
            for j, (toy, color) in enumerate(toys_at_location):
                toy_y = y + toy_height * (j + 1)
                draw.text((x + location_width / 2, toy_y - toy_height / 2 - text_padding), toy, fill=color, anchor='middle')
        else:
            draw.text((x + location_width / 2, y + toy_size / 2 - toy_size / 4 + label_spacing), 'Empty', fill='black', anchor='middle')

    # Draw the robot's location
    robot_x = padding + (game_state['robotLocation'] - 1) * location_width + location_width / 2
    robot_y = padding
    robot_size = toy_size / 2
    robot_polygon = [(robot_x - robot_size, robot_y + robot_size), (robot_x + robot_size, robot_y + robot_size), (robot_x, robot_y)]
    draw.polygon(robot_polygon, fill='blue')

    # Draw the 'robot' label
    draw.text((robot_x, robot_y + robot_size + text_padding), 'Robot', fill='black', anchor='middle')

    # Draw the robot's arm position
    arm_x = padding + (game_state['robotArm'] - 1) * location_width + location_width / 2
    arm_y = robot_y + robot_size + 2 * text_padding
    draw.rectangle([arm_x - 10, arm_y, arm_x + 10, arm_y + 30], fill='gray', outline='black')
    draw.text((arm_x, arm_y + 15), 'Arm', fill='black', anchor='middle')

    # Draw the pick actions left
    draw.text((padding, height - padding - toy_size - 70), f"Pick Actions Left: {game_state['pickActionsLeft']}", fill='black')

    # Show the image
    image.show()
