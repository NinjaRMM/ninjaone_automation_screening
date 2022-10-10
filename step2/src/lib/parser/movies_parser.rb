# frozen_string_literal: true

# Find movies from a source database
class MoviesFinder
  # Selects the movies according to a target decade
  # @param movies [Array<Hash>] The source movies list
  # @param target_decade [Integer] The target decade
  # @return [Array<Hash>] The movies from the target decade
  def self.movies_from_decade(movies:, target_decade:)
    movies.select { |movie| decade(movie['year'].to_i) == target_decade }
  end

  # Obtains the decade from a given year
  # @param year [Integer] The source year
  # @return [Integer] The decade of the given year
  def self.decade(year)
    (year / 10 * 10).to_s[-2..].to_i
  end
end
