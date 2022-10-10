# frozen_string_literal: true

require 'json'
require_relative 'parser/input_handler'
require_relative 'parser/movies_parser'

options = InputHandler.parse_input(ARGV)

begin
  source_file_path = '..\..\..\data\\'
  source_file_name = 'movies.json'
  file = File.read(source_file_path + source_file_name)
  parser_file = JSON.parse(file)

  filtered_movies = MoviesFinder.movies_from_decade(movies: parser_file, target_decade: options[:decade])

  File.write(source_file_path + options[:output_file_name], JSON.dump(filtered_movies))
rescue Exception => e
  puts "Parsing Failed due to: \n #{e.message}"
  exit 1
end
