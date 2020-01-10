package br.com.maddytec.marte.utils;

public enum DirecaoEnum {

	N{
		@Override
		public DirecaoEnum alterarDirecao(String novaDirecao) {
			if(novaDirecao.equals(ComandoEnum.L.name())) {
            	 return W;
            } else if(novaDirecao.equals(ComandoEnum.R.name())) {
            	return E;
            }
			return null;
		}
	},
	E{
		@Override
		public DirecaoEnum alterarDirecao(String novaDirecao) {
			if(novaDirecao.equals(ComandoEnum.L.name())) {
            	 return N;
            } else if(novaDirecao.equals(ComandoEnum.R.name())) {
            	return S;
            }
			return null;
		}
	},
	S{
		@Override
		public DirecaoEnum alterarDirecao(String novaDirecao) {
			if(novaDirecao.equals(ComandoEnum.L.name())) {
            	 return E;
            } else if(novaDirecao.equals(ComandoEnum.R.name())) {
            	return W;
            }
			return null;
		}
	},
	W{
		@Override
		public DirecaoEnum alterarDirecao(String novaDirecao) {
			if(novaDirecao.equals(ComandoEnum.L.name())) {
            	 return S;
            } else if(novaDirecao.equals(ComandoEnum.R.name())) {
            	return N;
            }
			return null;
		}
		
	};
	
	public abstract DirecaoEnum alterarDirecao(String novaDirecao);
}
