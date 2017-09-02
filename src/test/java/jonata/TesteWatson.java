package jonata;

import com.ibm.watson.developer_cloud.natural_language_understanding.v1.NaturalLanguageUnderstanding;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.AnalysisResults;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.AnalyzeOptions;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.Features;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.SentimentOptions;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author JonataBecker
 */
public class TesteWatson {

    public static void main(String[] args) {

        NaturalLanguageUnderstanding service = new NaturalLanguageUnderstanding(
                NaturalLanguageUnderstanding.VERSION_DATE_2017_02_27,
                "35ab4377-2c9a-448c-83ab-d4583503bb6f",
                "NOvvgbAGbAT3"
        );

//        String text = "Olha como eu estou feliz";
        String text = "Olha como eu estou triste";

        SentimentOptions sentiment = new SentimentOptions.Builder()
                .build();

        Features features = new Features.Builder()
                .sentiment(sentiment)
                .build();

        AnalyzeOptions parameters = new AnalyzeOptions.Builder()
                .text(text)
                .features(features)
                .build();

        AnalysisResults response = service
                .analyze(parameters)
                .execute();
        System.out.println(response);

    }

}
