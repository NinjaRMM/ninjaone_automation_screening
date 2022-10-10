require 'optparse'
require 'pry'

# Handles the CLI input
class InputHandler
  # Parses the Input
  def self.parse_input(options)
    args = {}
    opt_parser = OptionParser.new do |opts|
      opts.banner = 'Usage:'
      opts.on('-d','--decade DECADE', 'Target Movie Decade', String) do |decade|

        puts decade
        args[:decade] = decade.to_i
      end
      opts.on('--output OUTPUT', 'The name of the output file to be created in the Data Folder', String) do |output_file_name|
        puts output_file_name
        args[:output_file_name] = output_file_name
      end
      opts.on('-h', '--help', 'Prints the Helper') do
        puts opts
        exit
      end
    end

    opt_parser.parse!(options)

    # Required validation
    missing_required = false
    missing_required = true if args[:decade].nil? || args[:output_file_name].nil?
    if missing_required
      puts opt_parser.help
      exit 1
    end

    args
  end
end
