import json
import sys

if len(sys.argv) < 3:
    print("Usage: python3 JsonCodeCreator.py <input_file> <output_file>")
    sys.exit(1)

input_file, output_file = sys.argv[1], sys.argv[2]

with open(input_file,"r") as f:
    code = f.read()

data = {
    'code': code
}



json_str = json.dumps(data, indent=4, ensure_ascii=False)
json_str = json_str.replace("\\n", "\n")

with open(output_file, "w") as json_file:
    json_file.write(json_str)