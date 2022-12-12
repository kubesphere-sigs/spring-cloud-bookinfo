package sample;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class DetailsService {

    private static List<Detail> details = Arrays.asList(
            new Detail(1, "William Shakespeare", 1595, "paperback", 200, "PublisherA",
                    "English", "1234567890", "123-1234567890","The Comedy of Errors","<a href=\"https://en.wikipedia.org/wiki/The_Comedy_of_Errors\">Wikipedia Summary</a>: The Comedy of Errors is one of <b>William Shakespeare\'s</b> early plays. It is his shortest and one of his most farcical comedies, with a major part of the humour coming from slapstick and mistaken identity, in addition to puns and word play."));

    public Optional<Detail> getDetailById(int id) {
        return details.stream()
                .filter(detail -> detail.getId() == id)
                .findFirst();
    }

}
