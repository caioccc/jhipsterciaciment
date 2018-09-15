import { browser, ExpectedConditions as ec } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { ClienteComponentsPage, ClienteDeleteDialog, ClienteUpdatePage } from './cliente.page-object';

describe('Cliente e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let clienteUpdatePage: ClienteUpdatePage;
    let clienteComponentsPage: ClienteComponentsPage;
    let clienteDeleteDialog: ClienteDeleteDialog;

    beforeAll(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Clientes', async () => {
        await navBarPage.goToEntity('cliente');
        clienteComponentsPage = new ClienteComponentsPage();
        expect(await clienteComponentsPage.getTitle()).toMatch(/Clientes/);
    });

    it('should load create Cliente page', async () => {
        await clienteComponentsPage.clickOnCreateButton();
        clienteUpdatePage = new ClienteUpdatePage();
        expect(await clienteUpdatePage.getPageTitle()).toMatch(/Create or edit a Cliente/);
        await clienteUpdatePage.cancel();
    });

    it('should create and save Clientes', async () => {
        await clienteComponentsPage.clickOnCreateButton();
        await clienteUpdatePage.setNomeInput('nome');
        expect(await clienteUpdatePage.getNomeInput()).toMatch('nome');
        await clienteUpdatePage.setTelefoneInput('telefone');
        expect(await clienteUpdatePage.getTelefoneInput()).toMatch('telefone');
        await clienteUpdatePage.setEnderecoInput('endereco');
        expect(await clienteUpdatePage.getEnderecoInput()).toMatch('endereco');
        await clienteUpdatePage.save();
        expect(await clienteUpdatePage.getSaveButton().isPresent()).toBeFalsy();
    });

    it('should delete last Cliente', async () => {
        const nbButtonsBeforeDelete = await clienteComponentsPage.countDeleteButtons();
        await clienteComponentsPage.clickOnLastDeleteButton();

        clienteDeleteDialog = new ClienteDeleteDialog();
        expect(await clienteDeleteDialog.getDialogTitle()).toMatch(/Are you sure you want to delete this Cliente?/);
        await clienteDeleteDialog.clickOnConfirmButton();

        expect(await clienteComponentsPage.countDeleteButtons()).toBe(nbButtonsBeforeDelete - 1);
    });

    afterAll(async () => {
        await navBarPage.autoSignOut();
    });
});
