import { element, by, ElementFinder } from 'protractor';

export class ProdutoComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-produto div table .btn-danger'));
    title = element.all(by.css('jhi-produto div h2#page-heading span')).first();

    async clickOnCreateButton() {
        await this.createButton.click();
    }

    async clickOnLastDeleteButton() {
        await this.deleteButtons.last().click();
    }

    async countDeleteButtons() {
        return this.deleteButtons.count();
    }

    async getTitle() {
        return this.title.getText();
    }
}

export class ProdutoUpdatePage {
    pageTitle = element(by.id('jhi-produto-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    codigoInput = element(by.id('field_codigo'));
    nomeInput = element(by.id('field_nome'));
    tipoInput = element(by.id('field_tipo'));
    categoriaInput = element(by.id('field_categoria'));
    pesoInput = element(by.id('field_peso'));
    marcaInput = element(by.id('field_marca'));
    descricaoInput = element(by.id('field_descricao'));
    instrucoesInput = element(by.id('field_instrucoes'));
    tipoEmbalagemInput = element(by.id('field_tipoEmbalagem'));

    async getPageTitle() {
        return this.pageTitle.getText();
    }

    async setCodigoInput(codigo) {
        await this.codigoInput.sendKeys(codigo);
    }

    async getCodigoInput() {
        return this.codigoInput.getAttribute('value');
    }

    async setNomeInput(nome) {
        await this.nomeInput.sendKeys(nome);
    }

    async getNomeInput() {
        return this.nomeInput.getAttribute('value');
    }

    async setTipoInput(tipo) {
        await this.tipoInput.sendKeys(tipo);
    }

    async getTipoInput() {
        return this.tipoInput.getAttribute('value');
    }

    async setCategoriaInput(categoria) {
        await this.categoriaInput.sendKeys(categoria);
    }

    async getCategoriaInput() {
        return this.categoriaInput.getAttribute('value');
    }

    async setPesoInput(peso) {
        await this.pesoInput.sendKeys(peso);
    }

    async getPesoInput() {
        return this.pesoInput.getAttribute('value');
    }

    async setMarcaInput(marca) {
        await this.marcaInput.sendKeys(marca);
    }

    async getMarcaInput() {
        return this.marcaInput.getAttribute('value');
    }

    async setDescricaoInput(descricao) {
        await this.descricaoInput.sendKeys(descricao);
    }

    async getDescricaoInput() {
        return this.descricaoInput.getAttribute('value');
    }

    async setInstrucoesInput(instrucoes) {
        await this.instrucoesInput.sendKeys(instrucoes);
    }

    async getInstrucoesInput() {
        return this.instrucoesInput.getAttribute('value');
    }

    async setTipoEmbalagemInput(tipoEmbalagem) {
        await this.tipoEmbalagemInput.sendKeys(tipoEmbalagem);
    }

    async getTipoEmbalagemInput() {
        return this.tipoEmbalagemInput.getAttribute('value');
    }

    async save() {
        await this.saveButton.click();
    }

    async cancel() {
        await this.cancelButton.click();
    }

    getSaveButton(): ElementFinder {
        return this.saveButton;
    }
}

export class ProdutoDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-produto-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-produto'));

    async getDialogTitle() {
        return this.dialogTitle.getText();
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
