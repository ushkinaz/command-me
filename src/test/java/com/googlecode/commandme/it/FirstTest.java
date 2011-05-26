package com.googlecode.commandme.it;

import com.googlecode.commandme.CLIParser;
import com.googlecode.commandme.annotations.Operand;
import com.googlecode.commandme.annotations.Option;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * First wave integration test.
 * Support for primitive types, only full names, no help, no shortcuts.
 *
 * @author Dmitry Sidorenko
 */
public class FirstTest {
    @SuppressWarnings({"UnusedDeclaration"})
    private static final Logger LOGGER = LoggerFactory.getLogger(FirstTest.class);

    private String[] arg1 = {
            "commit",
            "verify",
            "--comment",
            "Add me",
            "--verbose",
            "--revision",
            "34"
    };

    @Test
    public void test1() throws Exception {
        Module module = CLIParser.createModule(Module.class).execute(arg1);

        assertThat(module.isCommitCalled(), is(true));
//        assertThat(module.isReallyBadNameCalled(), is(true));
        assertThat(module.isVerifyCalled(), is(true));
//        assertThat(module.isFormatCCalled(), is(false));
//        assertThat(module.isDestroyTheWorldCalled(), is(false));

        assertThat(module.isVerbose(), is(true));
        assertThat(module.getComment(), is("Add me"));
        assertThat(module.getRevision(), is(34));
    }

    @SuppressWarnings({"UnusedDeclaration"})
    public static class Module {
        private String comment;
        private int    revision;
        private boolean verbose = false;

        private boolean verifyCalled = false;
        private boolean commitCalled = false;
        private boolean reallyBadNameCalled;
        private boolean formatCCalled;
        private boolean destroyTheWorldCalled;

        public Module() {
        }

        public String getComment() {
            return comment;
        }

        @Option(shortName = "m")
        public void setComment(String comment) {
            this.comment = comment;
        }

        public int getRevision() {
            return revision;
        }

        @Option
        public void setRevision(int revision) {
            this.revision = revision;
        }

        public boolean isVerifyCalled() {
            return verifyCalled;
        }

        public boolean isCommitCalled() {
            return commitCalled;
        }

        @Operand(name = "verify")
        public void doVerify() {
            verifyCalled = true;
        }

        @Operand
        public void commit() {
            commitCalled = true;
        }

        @Operand(name = "format")
        public void formatC() {
            formatCCalled = true;
        }

        @Operand(defaultOperand = true)
        public void destroyTheWorld() {
            destroyTheWorldCalled = true;
        }

        @Operand(name = "make")
        public void reallyBadName() {
            reallyBadNameCalled = true;
        }

        public boolean isVerbose() {
            return verbose;
        }

        @Option
        public void setVerbose(boolean verbose) {
            this.verbose = verbose;
        }

        public boolean isReallyBadNameCalled() {
            return reallyBadNameCalled;
        }

        public boolean isFormatCCalled() {
            return formatCCalled;
        }

        public boolean isDestroyTheWorldCalled() {
            return destroyTheWorldCalled;
        }
    }
}