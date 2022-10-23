import argparse


def parse_arguments():
    """
    Parse arguments
    :return:parsed arguments namespace
    """
    parser = argparse.ArgumentParser(description='Python tool for overall win32 actions')
    parser.add_argument('--json_path',
                        required=True,
                        action="store", help="Movies json file path")
    parser.add_argument('--decade',
                        required=True,
                        action="store", help="Decade to filter the movies")
    parser.add_argument('--output',
                        action="store", help="Output result")

    parsed_args = parser.parse_args()
    parsed_args.decade = parsed_args.decade if parsed_args.decade.endswith('s') else parsed_args.decade + 's'

    if not parsed_args.decade.endswith('0s'):
        parser.error("Invalid decade")
    return parsed_args
