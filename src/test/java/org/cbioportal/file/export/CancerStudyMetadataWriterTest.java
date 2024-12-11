package org.cbioportal.file.export;

import org.cbioportal.file.model.CancerStudyMetadata;
import org.junit.Test;

import java.io.StringWriter;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

public class CancerStudyMetadataWriterTest {

    StringWriter output = new StringWriter();
    KeyValueConfigurationWriter writer = new KeyValueConfigurationWriter(output);

    @Test
    public void testAllFields() {
       writer.writeRecord(new CancerStudyMetadata(
            "toc",
            "study_id1",
            "study name",
            "study description",
            Optional.of("Citation"),
            Optional.of("1234"),
            Optional.of("GROUP1;GROUP2"),
            Optional.of(true),
            Optional.of("tags_file.txt"),
            Optional.of("hg38")
        ));
       
       assertEquals("""
           type_of_cancer: toc
           cancer_study_identifier: study_id1
           name: study name
           description: study description
           citation: Citation
           pmid: 1234
           groups: GROUP1;GROUP2
           add_global_case_list: true
           tags_file: tags_file.txt
           reference_genome: hg38
           """, output.toString());
    }
}
