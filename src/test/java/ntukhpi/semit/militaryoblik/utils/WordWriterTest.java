package ntukhpi.semit.militaryoblik.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WordWriterTest {

    WordWriter wordWriter = null;

    @BeforeEach
    void setUp() {
        wordWriter = new WordWriter();
    }

    @Test
    void readAndWriteFile() {
        wordWriter.readAndWriteFile();
    }
}