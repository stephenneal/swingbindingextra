package com.swing.binding.bbb;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;
import java.util.Date;

import org.jdesktop.beansbinding.Binding;
import org.junit.Test;

import com.swing.test.TestUtils;
import com.swing.binding.TestBean;
import com.toedter.calendar.JDateChooser;

/**
 * Tests the functionality of {@link ToedterDateBinding}.
 * <p>
 * This does not test the class in isolation (as per a unit test), it tests with real bindings (BetterBeansBinding).
 * </p>
 * 
 * @author Stephen Neal
 * @since 18/07/2011
 */
public class ToedterDateBindingFunctionalTest {

    /**
     * Test for {@link ToedterDateBinding#date(Object, String, JDateChooser)}. Verifies binding updates correctly in
     * both directions.
     */
    @Test
    public void testDateDateChooser() {
        // Setup
        final TestBean bean = new TestBean();
        final JDateChooser dateField = new JDateChooser();

        // Bind
        Binding<TestBean, Date, JDateChooser, Date> binding = ToedterDateBinding.date(bean, "date", dateField);
        binding.bind();

        // Test
        assertEquals(null, bean.getString());
        assertEquals(null, dateField.getDate());

        // Update the bean value
        final Calendar cal = Calendar.getInstance();
        final Date value = cal.getTime();
        bean.setDate(value);
        // The following test fails periodically as a result of timing (perhaps there is an issue with the date
        // chooser?) For now add a sleep.
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
        }
        TestUtils.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                assertEquals(value, dateField.getDate());
            }
        });
        // Clear the date chooser
        dateField.setDate(null);
        TestUtils.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                assertEquals(null, bean.getDate());
            }
        });
        // Update the date chooser with a value
        dateField.setDate(value);
        TestUtils.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                assertEquals(value, bean.getDate());
            }
        });
        // Clear the bean value
        bean.setDate(null);
        TestUtils.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                assertEquals(null, dateField.getDate());
            }
        });
    }

}
