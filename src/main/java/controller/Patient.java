package controller;

public class Patient {
	private Long code;

	private String patientName;

	private String nameMedicine;

	private String noOfTablets;

	public Long getCode() {
		return code;
	}

//	public Patient(String code, String patientName, String nameMedicine, String noOfTablets) {
//		super();
//		this.code = code;
//		this.patientName = patientName;
//		this.nameMedicine = nameMedicine;
//		this.noOfTablets = noOfTablets;
//	}
//
//	public Patient() {
//
//	}

	public void setCode(Long code) {
		this.code = code;
	}

	public String getPatientName() {
		return patientName;
	}

	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}

	public String getNameMedicine() {
		return nameMedicine;
	}

	public void setNameMedicine(String nameMedicine) {
		this.nameMedicine = nameMedicine;
	}

	public String getNoOfTablets() {
		return noOfTablets;
	}

	public void setNoOfTablets(String noOfTablets) {
		this.noOfTablets = noOfTablets;
	}

	@Override
	public String toString() {
		return "Patient [code=" + code + ", patientName=" + patientName + ", nameMedicine=" + nameMedicine
				+ ", noOfTablets=" + noOfTablets + "]";
	}

//	@Override
//	public int hashCode() {
//		final int prime = 31;
//		int result = 1;
//		result = prime * result + ((patientName == null) ? 0 : patientName.hashCode());
//		return result;
//	}
//
//	@Override
//	public boolean equals(Object obj) {
//		if (this == obj)
//			return true;
//		if (obj == null)
//			return false;
//		if (getClass() != obj.getClass())
//			return false;
//		Patient other = (Patient) obj;
//		if (patientName == null) {
//			if (other.patientName != null)
//				return false;
//		} else if (!patientName.equals(other.patientName))
//			return false;
//		return true;
//	}

	
}
