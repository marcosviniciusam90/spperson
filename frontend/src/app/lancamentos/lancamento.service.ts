import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import * as moment from 'moment';
import { environment } from 'src/environments/environment';
import { Lancamento } from './lancamento.model';

export class LancamentoFiltro {
  descricao: string;
  dataVencimentoInicio: Date;
  dataVencimentoFim: Date;
  pagina: number;
  itensPorPagina = 5;
}

@Injectable({
  providedIn: 'root'
})
export class LancamentoService {

  lancamentosUrl = `${environment.apiUrl}/lancamentos`;

  constructor(
    private http: HttpClient
  ) { }

  pesquisar(filtro: LancamentoFiltro): Promise<any> {
    let params = new HttpParams();

    // parâmetros de paginação
    params = params.set('page', filtro.pagina.toString());
    params = params.set('size', filtro.itensPorPagina.toString());

    // parâmetros do filtro de pesquisa
    if (filtro.descricao) {
      params = params.set('descricao', filtro.descricao);
    }

    if (filtro.dataVencimentoInicio) {
      params = params.set('dataVencimentoDe',
        moment(filtro.dataVencimentoInicio).format('YYYY-MM-DD'));
    }

    if (filtro.dataVencimentoFim) {
      params = params.set('dataVencimentoAte',
        moment(filtro.dataVencimentoFim).format('YYYY-MM-DD'));
    }

    return this.http.get(`${this.lancamentosUrl}?resumo`, { params })
      .toPromise();
  }

  excluir(codigo: number): Promise<any> {
    return this.http.delete(`${this.lancamentosUrl}/${codigo}`)
      .toPromise();
  }

  adicionar(lancamento: Lancamento): Promise<Lancamento> {
    return this.http.post<Lancamento>(this.lancamentosUrl, lancamento)
      .toPromise();
  }

  atualizar(lancamento: Lancamento): Promise<Lancamento> {
    return this.http.put<Lancamento>(`${this.lancamentosUrl}/${lancamento.codigo}`, lancamento)
      .toPromise()
      .then(response => {
        const lancamentoAlterado = response;

        this.converterStringsParaDatas([lancamentoAlterado]);

        return lancamentoAlterado;
      });
  }

  buscarPorCodigo(codigo: number): Promise<Lancamento> {
    return this.http.get<Lancamento>(`${this.lancamentosUrl}/${codigo}`)
      .toPromise()
      .then(response => {
        const lancamento = response;

        this.converterStringsParaDatas([lancamento]);

        return lancamento;
      });
  }

  private converterStringsParaDatas(lancamentos: Lancamento[]): void {
    for (const lancamento of lancamentos) {
      lancamento.dataVencimento = moment(lancamento.dataVencimento,
        'YYYY-MM-DD').toDate();

      if (lancamento.dataPagamento) {
        lancamento.dataPagamento = moment(lancamento.dataPagamento,
          'YYYY-MM-DD').toDate();
      }
    }
  }
}
