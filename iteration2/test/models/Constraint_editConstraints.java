package iteration2.test.models;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;

import org.junit.Test;

import iteration2.src.models.Constraint;


public class Constraint_editConstraints {
    
    @Test
    public void editConstraint() {
        HashMap<Integer, String> editedAttributes = new HashMap<Integer, String>();
        editedAttributes.put(1, "5");
        editedAttributes.put(2, "true");
        editedAttributes.put(3, "10");
        
        Constraint constraint = new Constraint(10, false, 5);
        constraint.editConstraint(editedAttributes);
        
        assertEquals(5, constraint.getMaxNumberOfCoursesStudentTake());
        assertEquals(true, constraint.getAddDropWeek());
        assertEquals(10, constraint.getMinRequiredECTSForTermProject());
    }

}
