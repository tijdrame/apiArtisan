import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { CommandeService } from 'app/entities/commande/commande.service';
import { ICommande, Commande } from 'app/shared/model/commande.model';

describe('Service Tests', () => {
  describe('Commande Service', () => {
    let injector: TestBed;
    let service: CommandeService;
    let httpMock: HttpTestingController;
    let elemDefault: ICommande;
    let expectedResult: ICommande | ICommande[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(CommandeService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Commande(0, currentDate, 0, false);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            dateCommande: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Commande', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            dateCommande: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateCommande: currentDate,
          },
          returnedFromService
        );

        service.create(new Commande()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Commande', () => {
        const returnedFromService = Object.assign(
          {
            dateCommande: currentDate.format(DATE_TIME_FORMAT),
            quantite: 1,
            deleted: true,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateCommande: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Commande', () => {
        const returnedFromService = Object.assign(
          {
            dateCommande: currentDate.format(DATE_TIME_FORMAT),
            quantite: 1,
            deleted: true,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateCommande: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Commande', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
