import { browser, ExpectedConditions as ec, protractor } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { PedidoComponentsPage, PedidoDeleteDialog, PedidoUpdatePage } from './pedido.page-object';

describe('Pedido e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let pedidoUpdatePage: PedidoUpdatePage;
    let pedidoComponentsPage: PedidoComponentsPage;
    let pedidoDeleteDialog: PedidoDeleteDialog;

    beforeAll(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Pedidos', async () => {
        await navBarPage.goToEntity('pedido');
        pedidoComponentsPage = new PedidoComponentsPage();
        expect(await pedidoComponentsPage.getTitle()).toMatch(/Pedidos/);
    });

    it('should load create Pedido page', async () => {
        await pedidoComponentsPage.clickOnCreateButton();
        pedidoUpdatePage = new PedidoUpdatePage();
        expect(await pedidoUpdatePage.getPageTitle()).toMatch(/Create or edit a Pedido/);
        await pedidoUpdatePage.cancel();
    });

    it('should create and save Pedidos', async () => {
        await pedidoComponentsPage.clickOnCreateButton();
        await pedidoUpdatePage.formatoEntregaSelectLastOption();
        await pedidoUpdatePage.setDataInput('01/01/2001' + protractor.Key.TAB + '02:30AM');
        expect(await pedidoUpdatePage.getDataInput()).toContain('2001-01-01T02:30');
        await pedidoUpdatePage.setValorUnitarioInput('5');
        expect(await pedidoUpdatePage.getValorUnitarioInput()).toMatch('5');
        await pedidoUpdatePage.setPrazoInput('prazo');
        expect(await pedidoUpdatePage.getPrazoInput()).toMatch('prazo');
        const selectedFoiEntregue = pedidoUpdatePage.getFoiEntregueInput();
        if (await selectedFoiEntregue.isSelected()) {
            await pedidoUpdatePage.getFoiEntregueInput().click();
            expect(await pedidoUpdatePage.getFoiEntregueInput().isSelected()).toBeFalsy();
        } else {
            await pedidoUpdatePage.getFoiEntregueInput().click();
            expect(await pedidoUpdatePage.getFoiEntregueInput().isSelected()).toBeTruthy();
        }
        const selectedFoiVisualizado = pedidoUpdatePage.getFoiVisualizadoInput();
        if (await selectedFoiVisualizado.isSelected()) {
            await pedidoUpdatePage.getFoiVisualizadoInput().click();
            expect(await pedidoUpdatePage.getFoiVisualizadoInput().isSelected()).toBeFalsy();
        } else {
            await pedidoUpdatePage.getFoiVisualizadoInput().click();
            expect(await pedidoUpdatePage.getFoiVisualizadoInput().isSelected()).toBeTruthy();
        }
        const selectedSaiuEntrega = pedidoUpdatePage.getSaiuEntregaInput();
        if (await selectedSaiuEntrega.isSelected()) {
            await pedidoUpdatePage.getSaiuEntregaInput().click();
            expect(await pedidoUpdatePage.getSaiuEntregaInput().isSelected()).toBeFalsy();
        } else {
            await pedidoUpdatePage.getSaiuEntregaInput().click();
            expect(await pedidoUpdatePage.getSaiuEntregaInput().isSelected()).toBeTruthy();
        }
        await pedidoUpdatePage.setObservacoesInput('observacoes');
        expect(await pedidoUpdatePage.getObservacoesInput()).toMatch('observacoes');
        await pedidoUpdatePage.clienteSelectLastOption();
        await pedidoUpdatePage.vendedorSelectLastOption();
        await pedidoUpdatePage.save();
        expect(await pedidoUpdatePage.getSaveButton().isPresent()).toBeFalsy();
    });

    it('should delete last Pedido', async () => {
        const nbButtonsBeforeDelete = await pedidoComponentsPage.countDeleteButtons();
        await pedidoComponentsPage.clickOnLastDeleteButton();

        pedidoDeleteDialog = new PedidoDeleteDialog();
        expect(await pedidoDeleteDialog.getDialogTitle()).toMatch(/Are you sure you want to delete this Pedido?/);
        await pedidoDeleteDialog.clickOnConfirmButton();

        expect(await pedidoComponentsPage.countDeleteButtons()).toBe(nbButtonsBeforeDelete - 1);
    });

    afterAll(async () => {
        await navBarPage.autoSignOut();
    });
});
