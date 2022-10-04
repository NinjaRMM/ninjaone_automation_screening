import platform


def get_system_info():
    return {'platform': platform.platform(),
            'system': platform.system(),
            'release': platform.release(),
            'version': platform.version()}


if __name__ == '__main__':
    info = get_system_info()
    # can validate if data is as expected
    print(info)
    exit(0)
