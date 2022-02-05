package com.techprimers.springbatchexample.config;

import com.techprimers.springbatchexample.model.User;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

@Configuration
@EnableBatchProcessing
public class SpringBatchConfig {

    @Bean
    public Job job(JobBuilderFactory jobBuilderFactory,
                   StepBuilderFactory stepBuilderFactory,
                   ItemReader<User> itemReader,
                   ItemProcessor<User,User> itemProcessor,
                   ItemWriter<User> itemWriter){

        Step step=stepBuilderFactory.get("ETL-file-load")
                .<User,User>chunk(100)
                .reader(itemReader)
                .processor(itemProcessor)
                .writer(itemWriter)
                .build();  //Step consisits of a reader,processor,writer
        return jobBuilderFactory.get("ETL-Load")   //This is a ETL job(Extract,transform,load) ,so we provided the name for this job
                .incrementer(new RunIdIncrementer())  //This is the default incrementer provided to mark each run ,we can have a custom incrementer just implement JobParametersIncrementer
                .start(step)  //if we have multiple step then .flow(step1).next(step2)
                .build();  //Now by this whole command we created a Job
    }

    @Bean
    public FlatFileItemReader<User> itemReader(){
        FlatFileItemReader<User> flatFileItemReader=new FlatFileItemReader<>();
        flatFileItemReader.setResource(new FileSystemResource("src/main/resources/users.csv"));
        flatFileItemReader.setName("CSV-Reader");//
        flatFileItemReader.setLinesToSkip(1);
        flatFileItemReader.setLineMapper(lineMapper());
        return flatFileItemReader;
    }

    @Bean
    public LineMapper<User> lineMapper() {
        DefaultLineMapper<User> defaultLineMapper=new DefaultLineMapper<>();
        DelimitedLineTokenizer  delimitedLineTokenizer=new DelimitedLineTokenizer();
        delimitedLineTokenizer.setDelimiter(",");
        delimitedLineTokenizer.setStrict(false);
        delimitedLineTokenizer.setNames("id","name","dept","salary");

        BeanWrapperFieldSetMapper<User> beanWrapperFieldSetMapper =new BeanWrapperFieldSetMapper();
        beanWrapperFieldSetMapper.setTargetType(User.class);

        defaultLineMapper.setLineTokenizer(delimitedLineTokenizer);
        defaultLineMapper.setFieldSetMapper(beanWrapperFieldSetMapper);


        return defaultLineMapper;
    }


}
