package com.cor.cep.util;

/** This class contains static versions of the operations for type UBoolean
 *  It is required because Esper (a CEP language) can only invoke static operations
 */

public class UBooleans {
  
    /**
     * Getters (no setters allowed in order to respect canonical form!)
     */
    public static boolean getB(UBoolean b) {
		return b.getB(); 
	}

    public static double getC(UBoolean b) {
		return b.getC();
	}

   /*********
     * 
     * STATIC Operations
     */

    public static UBoolean unot(UBoolean b) {
	   return b.not();
   }

    public static UBoolean and(UBoolean b1, UBoolean b2) {
	   return b1.and(b2);
   }
    public static UBoolean or(UBoolean b1, UBoolean b2) {
	   return b1.or(b2);
   }
    public static UBoolean implies(UBoolean b1, UBoolean b2) {
	   return b1.implies(b2);
   }
    public static UBoolean xor(UBoolean b1, UBoolean b2) {
	   return b1.xor(b2);
   }
    public static UBoolean equivalent(UBoolean b1, UBoolean b2) {
	   return b1.equivalent(b2);
   }

    public static boolean equals(UBoolean b1, UBoolean b2) {
	   return b1.equals(b2);
   }

    public static boolean equals(UBoolean b1, UBoolean b2, double confidence) {
	   return b1.equals(b2,confidence);
   }

    public static boolean isCloseTo(UBoolean b1, UBoolean b2, double error) {
	   return b1.equals(b2,error);
   }

	/******
	 * STATIC Conversions
	 */
	public static String toString(UBoolean b) {
        return b.toString();
	}

	public static boolean toBoolean(UBoolean b) {
        return b.toBoolean();
	}

}
