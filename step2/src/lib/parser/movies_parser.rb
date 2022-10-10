
class MoviesParser
  def self.movies_from_decade( movies: , target_decade:)
    movies.select{|movie| decade(movie['year'].to_i) == target_decade }
  end

  private

  def self.decade(year)
    (year/10 * 10).to_s[-2..-1].to_i
  end
end