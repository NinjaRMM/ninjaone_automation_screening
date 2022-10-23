def filter_content_by_decade(data, decade):
    """
    Filter content by decade
    :param data: Array of movies
    :param decade: String as %i0s
    :return: list of filtered movies
    """
    return list(filter(lambda movie: year_to_decade(movie['year']) == decade, data))


def year_to_decade(value):
    """
    Filter content by decade
    :param value: Integer like 1984
    :return: decade value: String like 80s
    """
    return f"{str(int(value / 10) * 10)[-2:]}s"
