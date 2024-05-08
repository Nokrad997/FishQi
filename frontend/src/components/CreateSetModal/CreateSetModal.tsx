import React, { useEffect, useState } from 'react';
import './CreateSetModal.scss';
import { FishQInput } from '../FishQInput/FishQInput';
import useFishQSet from '../../hooks/useFisQSet';
import useFiles from '../../hooks/useFiles';
import useFishQ from '../../hooks/useFishQ';

interface Props {
  isOpen: boolean;
  onClose: () => void;
}

export const CreateSetModal: React.FC<Props> = ({ isOpen, onClose }) => {
  const [file, setFile] = useState<File | null>(null);
  const [inputs, setInputs] = useState<FishQData[]>([]);
  const [nextKey, setNextKey] = useState(0);
  const { sendSet, updateSet } = useFishQSet();
  const { sendFiles } = useFiles();
  const { sendFishQ } = useFishQ();
  const [error, setError] = useState<string>('');

  useEffect(() => {
    if (!isOpen) {
      setFile(null);
      setInputs([]);
      setNextKey(0);
    }
  }, [isOpen]);

  const handleFileSelect = (event: any): void => {
    if (event.target.files.length > 0) {
      const selectedFile = event.target.files[0];
      setFile(selectedFile);
    }
  };

  const triggerFileInput = (): void => {
    document.getElementById('fileInput').click();
  };

  const addFishQHandler = (): void => {
    setInputs((inputs) => [...inputs, { key: nextKey, word: '', translation: '' }]);
    setNextKey(nextKey + 1);
  };

  const deleteInputFieldHandler = (key: number): void => {
    setInputs((inputs) => inputs.filter((input) => input.key !== key));
  };

  const handleWordChange = (key: number, value: string): void => {
    const updatedInputs = inputs.map((input) => (input.key === key ? { ...input, word: value } : input));
    setInputs(updatedInputs);
  };

  const handleTranslationChange = (key: number, value: string): void => {
    const updatedInputs = inputs.map((input) => (input.key === key ? { ...input, translation: value } : input));
    setInputs(updatedInputs);
  };

  const submitHandler = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      const setData = {
        title: (document.getElementById('title') as HTMLInputElement).value,
        language: (document.getElementById('language') as HTMLInputElement).value,
        visibility: (document.getElementById('visibility') as HTMLSelectElement).value,
        description: (document.getElementById('description') as HTMLInputElement).value,
      }
      const setResponse = await sendSet(setData);
      console.log(setResponse);

      let i = 0;
      const fishqs = inputs.map(({ word, translation }) => ({
        key: i++,
        word: word,
        translation: translation
      }));

      const data = {
        setId: setResponse.setId,
        photo: file,
        fishqs,
      };

      console.log(data);
      const filesResponse = await sendFiles(data);

      const updateSetData = {
        setId: setResponse.setId,
        ftpImagePath: filesResponse[0].ftpPath,
      };

      const fishQWordsData = {
        setId: setResponse.setId,
        ftpWordsPath: filesResponse[1].ftpPath,
      };
      
      await updateSet(updateSetData);
      await sendFishQ(fishQWordsData);

    } catch (error: any) {
      setError(error.message);
    }
  };

  if (!isOpen) {
    return null;
  }

  return (
    <div className="createSet-modal">
      <div className="createSet-card">
        <img src="src/assets/icons/reject.png" alt="reject" className="reject-icon" width={40} onClick={onClose} />

        <form className="createSetForm" onSubmit={submitHandler}>
          <h1>Create a set of FishQ</h1>
          <div className="setInformation">
            <div className="imageContainer" onClick={triggerFileInput}>
              <img
                src={file ? URL.createObjectURL(file) : 'src/assets/icons/image.png'}
                alt="setImage"
                className="setImage"
                width={200}
                height={200}
              />
              <input
                type="file"
                id="fileInput"
                style={{ display: 'none' }}
                onChange={handleFileSelect}
                accept="image/*"
              />
            </div>

            <div className="setDetails">
              <input type="text" id="title" placeholder="Type in set title" required />
              <input type="text" id="language" placeholder="Type in set language" required />
              <select id="visibility" name="visibility" defaultValue="Choose visibility option">
                <option value="public">Public</option>
                <option value="private">Private</option>
              </select>
            </div>

            <div className="setDescription">
              <input type="text" id="description" placeholder="Write description for this set" />
            </div>
          </div>
          <div className="fisQInputs">
            {inputs.map(({ key, word, translation }) => (
              <FishQInput
                key={key}
                wordValue={word}
                translationValue={translation}
                onWordChange={(e) => handleWordChange(key, e.target.value)}
                onTranslationChange={(e) => handleTranslationChange(key, e.target.value)}
                fun={() => deleteInputFieldHandler(key)}
              />
            ))}
          </div>
          <button type="button" onClick={addFishQHandler}>
            <img src="src/assets/icons/add.png" alt="addFishQ" width={40} />
          </button>
          {error && <div className="error-message">{error}</div>}
          <button type="submit">Save</button>
        </form>
      </div>
    </div>
  );
};
