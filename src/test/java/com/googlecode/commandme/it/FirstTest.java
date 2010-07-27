package com.googlecode.commandme.it;

import com.googlecode.commandme.CLIParser;
import com.googlecode.commandme.annotations.Action;
import com.googlecode.commandme.annotations.Parameter;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.util.Date;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author Dmitry Sidorenko
 */
public class FirstTest {
    @SuppressWarnings({"UnusedDeclaration"})
    private static final Logger LOGGER = LoggerFactory.getLogger(FirstTest.class);

    private String[] arg1 = {
            "commit",
            "verify",
            "-m",
            "\"Add me\"",
            "--verbose",
            "--date",
            "26/02/2010",
            "-r",
            "34"
    };

    @Test
    public void test1() throws Exception {
        Module module = CLIParser.createModule(Module.class).execute(arg1);

        assertThat(module.isCommitCalled(), is(true));
        assertThat(module.isReallyBadNameCalled(), is(true));
        assertThat(module.isVerifyCalled(), is(true));
        assertThat(module.isFormatCCalled(), is(false));
        assertThat(module.isDestroyTheWorldCalled(), is(false));

        assertThat(module.isVerbose(), is(true));
        assertThat(module.getComment(), is("Add me"));
        assertThat(module.getRevision(), is(34));
        assertThat(module.getDate(), is(DateFormat.getDateInstance().parse("26/02/2010")));
    }

    @SuppressWarnings({"UnusedDeclaration"})
    public static class Module{
        private String comment;
        private int revision;
        private Date date;
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

        @Parameter(shortName = "m")
        public void setComment(String comment) {
            this.comment = comment;
        }

        public int getRevision() {
            return revision;
        }

        @Parameter
        public void setRevision(int revision) {
            this.revision = revision;
        }

        public Date getDate() {
            return date;
        }

        @Parameter
        public void setDate(Date date) {
            this.date = date;
        }

        public boolean isVerifyCalled() {
            return verifyCalled;
        }

        public boolean isCommitCalled() {
            return commitCalled;
        }

        @Action
        public void doVerify(){
            verifyCalled = true;
        }

        @Action
        public void commit(){
            commitCalled = true;
        }

        @Action(name = "format")
        public void formatC(){
            formatCCalled = true;
        }

        @Action(defaultAction = true)
         public void destroyTheWorld(){
             destroyTheWorldCalled = true;
         }

        @Action(name = "make")
        public void reallyBadName(){
            reallyBadNameCalled = true;
        }

        public boolean isVerbose() {
            return verbose;
        }

        @Parameter
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