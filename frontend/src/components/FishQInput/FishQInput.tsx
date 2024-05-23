import "./FishQInput.scss"

interface Props {
    wordValue?: string;
    translationValue?: string;
    onWordChange: (event: React.ChangeEvent<HTMLInputElement>) => void;
    onTranslationChange: (event: React.ChangeEvent<HTMLInputElement>) => void;
    fun: () => void;
}

export const FishQInput: React.FC<Props> = ({ wordValue, translationValue, onWordChange, onTranslationChange, fun }) => {
    return (
        <div className="fishQInput">
            <input 
                type="text"
                placeholder="word"
                required
                value={wordValue}
                onChange={onWordChange}
            />
            <input 
                type="text"
                placeholder="translation"
                required
                value={translationValue}
                onChange={onTranslationChange}
            />
            <img src="src/assets/icons/reject.png" alt="reject" className="reject" width={40} onClick={fun}/>
        </div>
    );
};