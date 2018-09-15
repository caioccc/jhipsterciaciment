import { browser, ExpectedConditions as ec, protractor } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { SaidaComponentsPage, SaidaDeleteDialog, SaidaUpdatePage } from './saida.page-object';

describe('Saida e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let saidaUpdatePage: SaidaUpdatePage;
    let saidaComponentsPage: SaidaComponentsPage;
    let saidaDeleteDialog: SaidaDeleteDialog;

    beforeAll(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Saidas', async () => {
        await navBarPage.goToEntity('saida');
        saidaComponentsPage = new SaidaComponentsPage();
        expect(await saidaComponentsPage.getTitle()).toMatch(/Saidas/);
    });

    it('should load create Saida page', async () => {
        await saidaComponentsPage.clickOnCreateButton();
        saidaUpdatePage = new SaidaUpdatePage();
        expect(await saidaUpdatePage.getPageTitle()).toMatch(/Create or edit a Saida/);
        await saidaUpdatePage.cancel();
    });

    it('should create and save Saidas', async () => {
        await saidaComponentsPage.clickOnCreateButton();
        await saidaUpdatePage.setEminenteInput('eminente');
        expect(await saidaUpdatePage.getEminenteInput()).toMatch('eminente');
        await saidaUpdatePage.setRazaoInput('razao');
        expect(await saidaUpdatePage.getRazaoInput()).toMatch('razao');
        await saidaUpdatePage.setValorInput('5');
        expect(await saidaUpdatePage.getValorInput()).toMatch('5');
        await saidaUpdatePage.formaSelectLastOption();
        await saidaUpdatePage.setDataInput('01/01/2001' + protractor.Key.TAB + '02:30AM');
        expect(await saidaUpdatePage.getDataInput()).toContain('2001-01-01T02:30');
        await saidaUpdatePage.setObservacoesInput('observacoes');
        expect(await saidaUpdatePage.getObservacoesInput()).toMatch('observacoes');
        await saidaUpdatePage.corSelectLastOption();
        await saidaUpdatePage.save();
        expect(await saidaUpdatePage.getSaveButton().isPresent()).toBeFalsy();
    });

    it('should delete last Saida', async () => {
        const nbButtonsBeforeDelete = await saidaComponentsPage.countDeleteButtons();
        await saidaComponentsPage.clickOnLastDeleteButton();

        saidaDeleteDialog = new SaidaDeleteDialog();
        expect(await saidaDeleteDialog.getDialogTitle()).toMatch(/Are you sure you want to delete this Saida?/);
        await saidaDeleteDialog.clickOnConfirmButton();

        expect(await saidaComponentsPage.countDeleteButtons()).toBe(nbButtonsBeforeDelete - 1);
    });

    afterAll(async () => {
        await navBarPage.autoSignOut();
    });
});
