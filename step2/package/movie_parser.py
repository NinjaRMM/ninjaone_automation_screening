import json

from src.filters.date_filter import filter_content_by_decade
from src.parser.arg_parser import parse_arguments



if __name__ == "__main__":
    args = parse_arguments()

    with open(args.json_path, encoding="utf-8") as json_file:
        data = json.load(json_file)
        result = filter_content_by_decade(data, args.decade)
        if args.output:
            with open(args.output, "w+", encoding="utf-8") as output_file:
                output_file.write(json.dumps(result))
        else:
            print(result)

