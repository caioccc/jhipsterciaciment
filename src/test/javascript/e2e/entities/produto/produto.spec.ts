import { browser, ExpectedConditions as ec } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { ProdutoComponentsPage, ProdutoDeleteDialog, ProdutoUpdatePage } from './produto.page-object';

describe('Produto e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let produtoUpdatePage: ProdutoUpdatePage;
    let produtoComponentsPage: ProdutoComponentsPage;
    let produtoDeleteDialog: ProdutoDeleteDialog;

    beforeAll(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Produtos', async () => {
        await navBarPage.goToEntity('produto');
        produtoComponentsPage = new ProdutoComponentsPage();
        expect(await produtoComponentsPage.getTitle()).toMatch(/Produtos/);
    });

    it('should load create Produto page', async () => {
        await produtoComponentsPage.clickOnCreateButton();
        produtoUpdatePage = new ProdutoUpdatePage();
        expect(await produtoUpdatePage.getPageTitle()).toMatch(/Create or edit a Produto/);
        await produtoUpdatePage.cancel();
    });

    it('should create and save Produtos', async () => {
        await produtoComponentsPage.clickOnCreateButton();
        await produtoUpdatePage.setCodigoInput('codigo');
        expect(await produtoUpdatePage.getCodigoInput()).toMatch('codigo');
        await produtoUpdatePage.setNomeInput('nome');
        expect(await produtoUpdatePage.getNomeInput()).toMatch('nome');
        await produtoUpdatePage.setTipoInput('tipo');
        expect(await produtoUpdatePage.getTipoInput()).toMatch('tipo');
        await produtoUpdatePage.setCategoriaInput('categoria');
        expect(await produtoUpdatePage.getCategoriaInput()).toMatch('categoria');
        await produtoUpdatePage.setPesoInput('peso');
        expect(await produtoUpdatePage.getPesoInput()).toMatch('peso');
        await produtoUpdatePage.setMarcaInput('marca');
        expect(await produtoUpdatePage.getMarcaInput()).toMatch('marca');
        await produtoUpdatePage.setDescricaoInput('descricao');
        expect(await produtoUpdatePage.getDescricaoInput()).toMatch('descricao');
        await produtoUpdatePage.setInstrucoesInput('instrucoes');
        expect(await produtoUpdatePage.getInstrucoesInput()).toMatch('instrucoes');
        await produtoUpdatePage.setTipoEmbalagemInput('tipoEmbalagem');
        expect(await produtoUpdatePage.getTipoEmbalagemInput()).toMatch('tipoEmbalagem');
        await produtoUpdatePage.save();
        expect(await produtoUpdatePage.getSaveButton().isPresent()).toBeFalsy();
    });

    it('should delete last Produto', async () => {
        const nbButtonsBeforeDelete = await produtoComponentsPage.countDeleteButtons();
        await produtoComponentsPage.clickOnLastDeleteButton();

        produtoDeleteDialog = new ProdutoDeleteDialog();
        expect(await produtoDeleteDialog.getDialogTitle()).toMatch(/Are you sure you want to delete this Produto?/);
        await produtoDeleteDialog.clickOnConfirmButton();

        expect(await produtoComponentsPage.countDeleteButtons()).toBe(nbButtonsBeforeDelete - 1);
    });

    afterAll(async () => {
        await navBarPage.autoSignOut();
    });
});
