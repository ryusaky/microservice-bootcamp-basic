package com.bootcamp.works.microservicebasic.util;

public class Global {

	public static class TipoSolicitud {
		public final static short AFILIACION = 1;
		public final static short APORTE = 2;
		public final static short RETIRO = 3;

	}

	public static class Errores {
		public final static int DUPLICATE_MODEL = 9001;
		public final static int BUSINESS_LOGIC_VALIDATION = 9002;
		public final static int NOT_FOUND_MODEL = 9003;
	}

	public static class Solicitud {
		public final static double MONTO_MINIMO_APORTE = 1.0;
		public final static double MONTO_MINIMO_RETIRO = 500.0 * 0.5;

	}
}
