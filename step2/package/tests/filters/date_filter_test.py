import unittest

from step2.package.src.filters.date_filter import filter_content_by_decade, year_to_decade


class DateFilterTest(unittest.TestCase):
    def test_82_decade_conversion(self):
        self.assertEqual(year_to_decade(1982), "80s")

    def test_85_decade_conversion(self):
        self.assertEqual(year_to_decade(1987), "80s")

    def test_92_decade_conversion(self):
        self.assertEqual(year_to_decade(1992), "90s")

    def test_filter_content_by_decade(self):
        input_dict = [{'year': 1972}, {'year': 1982}]
        expected_dict = [{'year': 1972}]

        result = filter_content_by_decade(input_dict, '70s')

        self.assertEqual(result, expected_dict)


if __name__ == '__main__':
    unittest.main()
