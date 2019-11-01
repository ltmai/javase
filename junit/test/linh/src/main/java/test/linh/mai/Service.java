package test.linh.mai;

class Service 
{
    public String toUpper(String s) 
    {
        return s.toUpperCase();
    } 
    
	/**
	 * Replaces substring by another string (with the same length) at given index.
	 * 
	 * @param s input string
	 * @param r replacement string
	 * @param i index at which replacement occurs
	 * @return the resulting string
	 */
	public String replaceAt(String s, String r, int i) {
		if (s == null) {
			return null;
		}

		if (r == null) {
			return s;
		}

		if ((i < 0) || i > s.length()) {
			return s;
		}

		return s.substring(0, i) + r + s.substring(i + r.length());
	}
}