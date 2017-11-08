package br.com.etico.ir.preditor;

import java.text.DecimalFormat;

import weka.classifiers.lazy.IBk;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

public class Preditor {
	
	
	public double[] fazerPrevisaoIBk(String tf, String idf, String tfidf) {
		DataSource ds = null;
		try {
			ds = new DataSource("/Users/thiagoluizrodrigues/Documents/IA/base.arff");
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Instances instance = null;
		try {
			instance = ds.getDataSet();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		instance.setClassIndex(3);
//		System.out.println(instance.toString());
		
		IBk bk = new IBk();
		try {
			bk.buildClassifier(instance);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
//		@attribute TF numeric
//		@attribute IDF numeric
//		@attribute TF-IDF numeric
//		@attribute CLASSE {ECONOMIA,ESPORTE}
//		,,,ECONOMIA
		
//		1.0000000000000038,1186.6378310700327,3.554740035581543,ESPORTE
//		0.9999999999999982,548.6109580738314,3.5220098577471775,ECONOMIA
		
		Instance novo = new DenseInstance(instance.numAttributes());
		novo.setDataset(instance);
		novo.setValue(0,Float.parseFloat(tf));
		novo.setValue(1,Float.parseFloat(idf));
		novo.setValue(2,Float.parseFloat(tfidf));
		
		double[] resultado = null;
		try {
			resultado = bk.distributionForInstance(novo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		DecimalFormat df = new DecimalFormat("#,###.0000");
//		
//		System.out.println("ECONOMIA:" + df.format(resultado[0] * 100));
//		System.out.println("ESPORTE:" + df.format(resultado[1] * 100));

		return resultado;
		
	}

	
	
	
	public static void main(String[] args) throws Exception {
		
		DataSource ds = new DataSource("/Users/thiagoluizrodrigues/Documents/IA/base.arff");
		Instances instance = ds.getDataSet();
		instance.setClassIndex(3);
//		System.out.println(instance.toString());
		
		IBk bk = new IBk();
		bk.buildClassifier(instance);
		
//		@attribute TF numeric
//		@attribute IDF numeric
//		@attribute TF-IDF numeric
//		@attribute CLASSE {ECONOMIA,ESPORTE}
//		,,,ECONOMIA
		
//		1.0000000000000038,1186.6378310700327,3.554740035581543,ESPORTE
//		0.9999999999999982,548.6109580738314,3.5220098577471775,ECONOMIA
		
		Instance novo = new DenseInstance(instance.numAttributes());
		novo.setDataset(instance);
		novo.setValue(0,Float.parseFloat("0.9999999999999982"));
		novo.setValue(1,Float.parseFloat("548.6109580738314"));
		novo.setValue(2,Float.parseFloat("5220098577471775"));
		
		double[] resultado = bk.distributionForInstance(novo);
		
		DecimalFormat df = new DecimalFormat("#,###.0000");
		
		System.out.println("ECONOMIA:" + df.format(resultado[0] * 100));
		System.out.println("ESPORTE:" + df.format(resultado[1] * 100));
		
	}
	
}
