def display(data, filename = None,is_merging_images = False):
    def flatten_dict(dictionary, parent_key='', sep='.'):
        items = []
        for k, v in dictionary.items():
            new_key = f"{parent_key}{sep}{k}" if parent_key else k
            if isinstance(v, dict):
                items.extend(flatten_dict(v, new_key, sep=sep).items())
            else:
                items.append((new_key, v))
        return dict(items)

    flattened_data = flatten_dict(data)
    max_key_length = max(len(key) for key in flattened_data.keys())

    print("| Key" + " " * (max_key_length - 3) + " | Value")
    print("|" + "-" * (max_key_length + 2) + "|" + "-" * 10)

    for key, value in flattened_data.items():
        print(f"| {key:<{max_key_length}} | {value}")