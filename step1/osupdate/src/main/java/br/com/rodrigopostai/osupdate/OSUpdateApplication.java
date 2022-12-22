package br.com.rodrigopostai.osupdate;

import br.com.rodrigopostai.osupdate.domain.OSUpdate;
import br.com.rodrigopostai.osupdate.os.OSFactory;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class OSUpdateApplication implements ApplicationRunner {

	public static void main(String[] args) {
		SpringApplication.run(OSUpdateApplication.class, args);
	}

    @Override
    public void run(ApplicationArguments arguments) throws Exception {
        List<OSUpdate> updates = OSFactory.getInstance().search();
        if (CollectionUtils.isNotEmpty(updates)) {
            updates.forEach(System.out::println);
        } else {
            System.out.println("No updates found!!!");
        }
        System.out.println("Finished!!!");
    }

}
