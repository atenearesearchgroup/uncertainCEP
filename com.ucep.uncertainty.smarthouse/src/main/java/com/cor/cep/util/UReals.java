package com.cor.cep.util;

/** This class contains static versions of the operations for type UReal
 *  It is required because Esper (a CEP language) can only invoke static operations
 */


public class UReals {
	
    /**
     * Setters and getters 
     */
    public static double getX(UReal r) {
		return r.getX(); 
	}
    public static void setX(UReal r, double x) {
		r.setX(x); 
	}
    public static double getU(UReal r) {
		return r.getU(); 
	}
	public static void setU(UReal r,double u) {
		r.setU(u); 
	}

 	/***
	 * STATIC OPERATIONS
	 */

	public static UReal add(UReal number1, UReal number2) {
		return number1.add(number2);
		
	}

	public static UReal minus(UReal number1, UReal number2) {
		return number1.minus(number2);
		
	}
	public static UReal mult(UReal number1, UReal number2) {
		return number1.mult(number2);
		
	}
	
	public static UReal mult(UReal number1, double number2) {
		UReal n2 = new UReal(number2, 0 );
		return number1.mult(n2);
		
	}
	
	public static UReal divideBy(UReal number1, UReal number2) {
		return number1.divideBy(number2);
		
	}
	
	public static UReal divideBy(UReal number1, double number2) {
		UReal n2 = new UReal(number2, 0);
		return number1.divideBy(n2);
	}
	
	public static UReal abs(UReal number1) {
		return number1.abs();
		
	}
	
	public static UReal neg(UReal number1) {
		return number1.neg();
		
	}
	
	public static UReal power(UReal number1, float s) {
		return number1.power(s);
	}

	public static UReal sqrt(UReal number1) {
		return number1.sqrt();
		
	}
	
	public static UReal inverse(UReal number1) {
		return number1.inverse();
	}
		
	public static UReal floor(UReal number1) {
		return number1.floor();
	}
		
	public static UReal round(UReal number1) {
		return number1.round();
	}
		
	/***
	 * comparison operations
	 */
/*
 	public static boolean lt(UReal number1, UReal number2) {
		return number1.lt(number2);
		
	}

	public static boolean le(UReal number1, UReal number2) {
		return number1.le(number2);
		
	}

	public static boolean gt(UReal number1, UReal number2) {
		return number1.gt(number2);
		
	}

	public static boolean ge(UReal number1, UReal number2) {
		return number1.ge(number2);
		
	}

 */
	public static boolean equals(UReal number1, UReal number2) {
		return number1.equals(number2);
		
	}
	public static boolean distinct(UReal number1, UReal number2) {
		return number1.distinct(number2);
		
	}
	/***
	 * comparison operations WITH ZERO = UReal(0.0)

	public static boolean ltZero(UReal number1) {
		return number1.ltZero();
		
	}
	public static boolean gtZero(UReal number1) {
		return number1.gtZero();
		
	}
	public static boolean leZero(UReal number1) {
		return number1.leZero();
		
	}
	public static boolean geZero(UReal number1) {
		return number1.geZero();
		
	}
	public static boolean equalsZero(UReal number1) {
		return number1.equalsZero();
		
	}
	public static boolean distinctZero(UReal number1) {
		return number1.distinctZero();
		
	}
	 */

	/*** 
	 *   FUZZY COMPARISON OPERATIONS
	 *   Assume UReal values (x,u) represent standard uncertainty values, i.e., they follow a Normal distribution
	 *   of mean x and standard deviation \sigma = u
	 */
	public static UBoolean le(UReal number1, UReal number2) {
		return number1.le(number2);
	}

	public static UBoolean gt(UReal number1, UReal number2) {
		return number1.gt(number2);
	}

	public static UBoolean ge(UReal number1, UReal number2) {
		return number1.ge(number2);
	}

	public static UBoolean ge(UReal number1, double number2) {
		UReal n2 = new UReal(number2, 0);
		return number1.ge(n2);
	}
	
	public static UBoolean lt(UReal number1, UReal number2) {
		return number1.lt(number2);
	}

	public static UBoolean uEquals(UReal number1, UReal number2) {
		return number1.uEquals(number2);
	}
	public static UBoolean uDistinct(UReal number1, UReal number2) {
		return number1.uDistinct(number2);
	}
	/***
	 * comparison operations WITH ZERO = UReal(0.0)
	 */
	public static boolean equalsZero(UReal number1) {
		return number1.equalsZero();
		
	}
	public static boolean distinctZero(UReal number1) {
		return number1.distinctZero();
		
	}
	public static UBoolean ltZero(UReal number1) {
		return number1.ltZero();
		
	}
	public static UBoolean gtZero(UReal number1) {
		return number1.gtZero();
		
	}
	public static UBoolean leZero(UReal number1) {
		return number1.leZero();
		
	}
	public static UBoolean geZero(UReal number1) {
		return number1.geZero();
		
	}
	public static UBoolean uEqualsZero(UReal number1) {
		return number1.uEqualsZero();
		
	}
	public static UBoolean uDistinctZero(UReal number1) {
		return number1.uDistinctZero();
	}
    
	/*** 
	 *   END OF FUZZY COMPARISON OPERATIONS WITH ZERO
	 */

	public static UReal min(UReal number1, UReal number2) {
		return number1.min(number2);
		
	}
	public static UReal max(UReal number1, UReal number2) {
		return number1.max(number2);
		
	}

	/******
	 * Conversions
	 */
	
	public static String toString(UReal r) {
		return r.toString();
	}
	
	public static int toInteger(UReal r){ //
		return r.toInteger();
	}
	
	public static double toReal(UReal r)  { 
		return r.toReal();
	}
	
	public static UInteger toUInteger(UReal r) {
		return r.toUInteger();
	}

	public static UInteger toBestUInteger(UReal r) {
		return r.toBestUInteger();		
	}

	public static UUnlimitedNatural toUUnlimitedNatural(UReal r) {
		return r.toUUnlimitedNatural();
	}

	public static UUnlimitedNatural toBestUUnlimitedNatural(UReal r) {
		return r.toBestUUnlimitedNatural();
	}


}
