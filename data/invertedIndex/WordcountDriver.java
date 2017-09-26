import org.apache.hadoop.conf.Configured;

import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

import java.util.StringTokenizer;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

public class WordcountDriver extends Configured implements Tool {
	public int run(String[] args) throws Exception {
		if (args.length != 2) {
			System.out.println("Usage: [input] [output]");
			System.exit(-1);
		}

		Job job = Job.getInstance(getConf());
		job.setJobName("wordcount");
		job.setJarByClass(WordcountDriver.class);
		
		/* Field separator for reducer output*/
		job.getConfiguration().set("mapreduce.output.textoutputformat.separator", " | ");
		
		
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);

		job.setMapperClass(WordcountMapper.class);
		job.setCombinerClass(WordcountReducer.class);
		job.setReducerClass(WordcountReducer.class);

		job.setInputFormatClass(TextInputFormat.class);
		job.setOutputFormatClass(TextOutputFormat.class);
		
		Path inputFilePath = new Path(args[0]);
		Path outputFilePath = new Path(args[1]);

		/* This line is to accept input recursively */
		FileInputFormat.setInputDirRecursive(job, true);

		FileInputFormat.addInputPath(job, inputFilePath);
		FileOutputFormat.setOutputPath(job, outputFilePath);


		/* Delete output filepath if already exists */
		FileSystem fs = FileSystem.newInstance(getConf());

		if (fs.exists(outputFilePath)) {
			fs.delete(outputFilePath, true);
		}
		
		return job.waitForCompletion(true) ? 0 : 1;
	}

	public static void main(String[] args) throws Exception {
		WordcountDriver wordcountDriver = new WordcountDriver();
		int res = ToolRunner.run(wordcountDriver, args);
	    System.exit(res);
	}

    public class WordcountMapper extends
            Mapper<LongWritable, Text, Text, Text> {

        private Text word = new Text();
        private Text filename = new Text();

        private boolean caseSensitive = false;

        @Override
        public void map(LongWritable key, Text value, Context context)
                throws IOException, InterruptedException {
            String filenameStr = ((FileSplit) context.getInputSplit()).getPath().getName();
            filename = new Text(filenameStr);
            
            String line = value.toString();

            if (!caseSensitive) {
                line = line.toLowerCase();
            }

            StringTokenizer tokenizer = new StringTokenizer(line);
            while (tokenizer.hasMoreTokens()) {
                word.set(tokenizer.nextToken());			
                context.write(word, filename);
            }
        }

        @Override
        protected void setup(Context context) throws IOException, InterruptedException {
            Configuration conf = context.getConfiguration();
            this.caseSensitive = conf.getBoolean("wordcount.case.sensitive",false);
        }
    }

    public class WordcountReducer extends Reducer<Text, Text, Text, Text> {

        @Override
        public void reduce(final Text key, final Iterable<Text> values,
                final Context context) throws IOException, InterruptedException {

            StringBuilder stringBuilder = new StringBuilder();

            for (Text value : values) {
                stringBuilder.append(value.toString());

                if (values.iterator().hasNext()) {
                    stringBuilder.append(" -> ");
                }
            }

            context.write(key, new Text(stringBuilder.toString()));
        }

    }
}
