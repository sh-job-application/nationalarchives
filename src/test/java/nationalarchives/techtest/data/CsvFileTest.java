package nationalarchives.techtest.data;

import org.assertj.core.util.Maps;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class CsvFileTest {
    @Test
    public void updatesSingleField() {
        CsvFile file = new CsvFileBuilder()
            .withHeaders("col1", "col2", "col3")
            .withRow("value1.1", "value1.2", "value1.3")
            .withRow("value2.1", "value2.2", "value2.3")
            .build();

        file.updateField("col3", 1, "new value");

        assertThat(file.getRows()).hasSize(2);
        assertThat(file.getRows().get(0).get("col3")).isEqualTo("new value");
    }

    @Test
    public void doesNotUpdateOtherFields() {
        CsvFile file = new CsvFileBuilder()
                .withHeaders("col1", "col2", "col3")
                .withRow("value1.1", "value1.2", "value1.3")
                .withRow("value2.1", "value2.2", "value2.3")
                .build();

        file.updateField("col3", 1, "new value");

        assertThat(file.getRows().get(0).get("col1")).isEqualTo("value1.1");
        assertThat(file.getRows().get(0).get("col2")).isEqualTo("value1.2");
        assertThat(file.getRows().get(1).get("col1")).isEqualTo("value2.1");
        assertThat(file.getRows().get(1).get("col2")).isEqualTo("value2.2");
        assertThat(file.getRows().get(1).get("col3")).isEqualTo("value2.3");
    }
}